package com.example.quizora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.databinding.ActivityPhase2Binding

class Phase2 : AppCompatActivity() {

    private lateinit var binding: ActivityPhase2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = ActivityPhase2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for buttons
        binding.student.setOnClickListener {
            navigateToPhase3()
        }

        binding.instructor.setOnClickListener {
            navigateToPhase3()
        }

        binding.parent.setOnClickListener {
            navigateToPhase3()
        }
    }

    private fun navigateToPhase3() {
        // Navigate to Phase2 activity
        val intent = Intent(this, Phase3::class.java)
        startActivity(intent)
    }
}
