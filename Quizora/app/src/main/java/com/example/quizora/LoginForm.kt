package com.example.quizora

import android.content.Intent
import android.os.Bundle
//import android.text.method.HideReturnsTransformationMethod
//import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizora.databinding.ActivityLoginFormBinding

class LoginForm : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding
    private var isPasswordVisible = false // Track password visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            // ✅ Proceed to MainActivity if input is valid
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // ✅ Toggle password visibility
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

//            // Check user in database
//            val userExists = checkUserInDatabase(email, password)
//
//            if (userExists) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            } else {
//                binding.EmailAddress.error = "Invalid credentials"
//                binding.Password.error = "Invalid credentials"
//            }
        }

        // (Replace with actual query)
        fun checkUserInDatabase(email: String, password: String): Boolean {
            return email == "user@example.com" && password == "password123"  // Replace with actual DB query
        }


        // ✅ Other buttons remain unchanged
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
}
