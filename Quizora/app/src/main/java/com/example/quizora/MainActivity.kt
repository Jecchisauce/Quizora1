package com.example.quizora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handling insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav1)

        // Load the default fragment only once
        if (savedInstanceState == null) {
            loadFragment(HomeFragment()) // Choose a default fragment
        }

        // Set up item selection listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_settings -> loadFragment(SettingsFragment())
                R.id.navigation_profile -> loadFragment(ProfileFragment())
                R.id.navigation_home -> loadFragment(HomeFragment())
                R.id.navigation_explore -> loadFragment(ExploreFragment())
                R.id.navigation_activity -> loadFragment(ActivityFragment())

                else -> false // Corrected position
            }
        }
    }

    // Function to load fragments
    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment) // Ensure container is defined in XML
            .commit()
        return true
    }
}

