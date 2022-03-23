package com.project.myexam

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

        val navView = findViewById<ChipNavigationBar>(R.id.nav_view)

        navView.setItemSelected(R.id.navigation_home, true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomepageFragment()).commit()


        bottomMenu(navView)
    }

    @SuppressLint("NonConstantResourceId")
    private fun bottomMenu(navView: ChipNavigationBar) {
        navView.setOnItemSelectedListener { i: Int ->
            var fragment: Fragment? = null
            when (i) {
                R.id.navigation_home -> fragment = HomepageFragment()
                R.id.navigation_maps -> fragment = MapsFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    fragment!!
                ).commit()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}