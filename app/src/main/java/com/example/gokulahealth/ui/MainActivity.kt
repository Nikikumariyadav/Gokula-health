package com.example.gokulahealth.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gokulahealth.R
import com.example.gokulahealth.databinding.ActivityMainBinding
import com.example.gokulahealth.ui.alerts.AlertsFragment
import com.example.gokulahealth.ui.cattle.CattleFragment
import com.example.gokulahealth.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "🐄 Gokula Health"

        if (savedInstanceState == null) loadFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home   -> { loadFragment(HomeFragment()); true }
                R.id.nav_cattle -> { loadFragment(CattleFragment()); true }
                R.id.nav_alerts -> { loadFragment(AlertsFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
