package com.example.quizora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.databinding.ActivityPhase3Binding

class Phase3 : AppCompatActivity() {

    private lateinit var binding: ActivityPhase3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = ActivityPhase3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for buttons
        binding.signbtn.setOnClickListener {
            navigateToSign_Up()
        }

        binding.loginbtn.setOnClickListener {
            //
        }

    }

    private fun navigateToSign_Up() {
        // Navigate to Phase2 activity
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}
