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
            navigateToSignUp()
        }

        binding.loginbtn.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        // Navigate to Login activity (assuming it's Phase2)
        val intent = Intent(this, LoginForm::class.java)
        startActivity(intent)
    }

    private fun navigateToSignUp() {
        // Navigate to SignUp activity
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}

