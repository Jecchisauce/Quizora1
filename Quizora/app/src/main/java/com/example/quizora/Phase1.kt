package com.example.quizora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.databinding.ActivityPhase1Binding

class Phase1 : AppCompatActivity() {

    private lateinit var binding: ActivityPhase1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = ActivityPhase1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for buttons
        binding.grade12.setOnClickListener {
            navigateToPhase2()
        }

        binding.university.setOnClickListener {
            navigateToPhase2()
        }

        binding.personal.setOnClickListener {
            // You can add logic here if needed for b3 button
        }
    }

    private fun navigateToPhase2() {
        // Navigate to Phase2 activity
        val intent = Intent(this, Phase2::class.java)
        startActivity(intent)
    }
}
