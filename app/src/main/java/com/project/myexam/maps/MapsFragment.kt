package com.project.myexam.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.myexam.R
import com.project.myexam.databinding.FragmentMapsBinding


class MapsFragment : Fragment() {

    private var binding: FragmentMapsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


}