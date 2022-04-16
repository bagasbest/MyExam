package com.project.myexam

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.project.myexam.databinding.ActivityMainBinding
import com.project.myexam.homepage.HomepageFragment
import com.project.myexam.maps.MapsFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = HomepageFragment()
            when (item.itemId) {
                R.id.navigation_home -> {
                    navView.menu.findItem(R.id.navigation_home).isEnabled = false
                    navView.menu.findItem(R.id.navigation_maps).isEnabled = true
                    selectedFragment = HomepageFragment()
                }

                R.id.navigation_maps -> {
                    navView.menu.findItem(R.id.navigation_home).isEnabled = true
                    navView.menu.findItem(R.id.navigation_maps).isEnabled = false
                    selectedFragment = MapsFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (selectedFragment != null) {
                transaction.replace(R.id.container, selectedFragment)
            }
            transaction.commit()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}