package com.project.myexam.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.myexam.R
import com.project.myexam.databinding.FragmentMapsBinding
import com.project.myexam.utils.DataModel
import com.project.myexam.utils.DataRepository
import java.util.*


class MapsFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapsBinding? = null
    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private var dataList: ArrayList<DataModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        fetchLocation()

        // auto request GPS to turn on
        MapsService.showLocationPrompt(activity)

        DataRepository.addItemFromJSON(dataList, requireActivity())

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchBtn?.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        val query = binding?.searchEt?.text.toString().trim().toLowerCase()

        if(query.isEmpty()) {
            Toast.makeText(activity, "Sorry, Location name must be filled", Toast.LENGTH_SHORT).show()
        } else {
            val progressDialog = ProgressDialog(activity)
            progressDialog.setCancelable(false)
            progressDialog.setMessage("Waiting until process finished")
            progressDialog.show()


            for(i in 0 until dataList.size) {

                if(dataList[i].tempName!!.contains(query)) {
                    progressDialog.dismiss()
                    val latitude: Double = dataList[i].latitude
                    val longitude: Double = dataList[i].longitude

                    Log.e("tag", "$latitude|$longitude")


                    val latLng = LatLng(latitude, longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    break
                } else if(i == dataList.size-1) {
                    progressDialog.dismiss()
                    Toast.makeText(activity, "Sorry, Location not exist", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                val supportMapFragment =
                    childFragmentManager.findFragmentById(R.id.google_maps) as SupportMapFragment
                supportMapFragment.getMapAsync(this)
            }
        }
    }


    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("Your Location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7f))
        mMap.addMarker(markerOptions)
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        }

        for (i in 0 until dataList.size) {
            val latitude: Double = dataList[i].latitude
            val longitude: Double = dataList[i].longitude
            val name = "" + dataList[i].name
            val address = "" + dataList[i].address
            val image = "" + dataList[i].image

            val latLng1 = LatLng(latitude, longitude)
            mMap.addMarker(
                MarkerOptions().position(latLng1).title(name)
                    .snippet("$address|$image")
            )
        }
        mMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                @SuppressLint("InflateParams") val row: View = layoutInflater.inflate(R.layout.custom_snippet, null)
                val img = row.findViewById<ImageView>(R.id.image)
                val name = row.findViewById<TextView>(R.id.name)
                val address = row.findViewById<TextView>(R.id.address)

                name.text = marker.title
                if (!marker.title.equals("Your Location")) {
                    img.visibility = View.VISIBLE
                    address.visibility = View.VISIBLE
                    Glide.with(requireActivity())
                        .load(
                            Objects.requireNonNull(marker.snippet)?.substring(marker.snippet!!.indexOf("|") + 1)
                        )
                        .into(img)
                    address.text = marker.snippet!!.substring(0, marker.snippet!!.indexOf("|"))
                } else {
                    img.visibility = View.GONE
                    address.visibility = View.GONE
                }
                return row
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LocationRequest.PRIORITY_HIGH_ACCURACY -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("Status", "On")
                } else {
                    Log.e("Status", "Off")
                }
            }
        }
    }


}