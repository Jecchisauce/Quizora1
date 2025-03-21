package com.example.quizora

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizora.databinding.ActivityLoginFormBinding

class LoginForm : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding
    private var isPasswordVisible = false // Track password visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#ADD8E6")

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        // Initialize View Binding
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Require input before signing in
        binding.Signin.setOnClickListener {
            val email = binding.EmailAddress.text.toString().trim()
            val password = binding.Password.text.toString().trim()

            if (email.isEmpty()) {
                binding.EmailAddress.error = "Email is required!"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.Password.error = "Password is required!"
                return@setOnClickListener
            }

            // ✅ Check user in database (Replace with actual query)
            if (checkUserInDatabase(email, password)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.EmailAddress.error = "Invalid credentials"
                binding.Password.error = "Invalid credentials"
            }
        }

        //  Toggle password visibility
        binding.togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.Password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.togglePassword.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to "eye off"
            } else {
                binding.Password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.togglePassword.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to "eye on"
            }
            binding.Password.setSelection(binding.Password.text.length) // Keep cursor at the end
        }

        //  Other buttons remain unchanged
        binding.Signupbtn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.forgotbtn.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        binding.bypassbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    // ✅ Simulated user database check (Replace with actual DB query)
    private fun checkUserInDatabase(email: String, password: String): Boolean {
        return email == "user@example.com" && password == "password123"  // Example credentials
    }
}
