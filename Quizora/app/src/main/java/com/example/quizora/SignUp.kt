package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.quizora.databinding.ActivitySignUpBinding
import com.example.quizora.fucntions.RetrofitClient
import com.example.quizora.fucntions.SignupReq
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var isPasswordVisible1 = false // Track Password1 visibility
    private var isPasswordVisible2 = false // Track Password2 visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Require all inputs before signing up
        binding.Signup.setOnClickListener {
            val nickname = binding.Nickname.text.toString().trim()
            val email = binding.EmailAddress.text.toString().trim()
            val password1 = binding.Password1.text.toString().trim()
            val password2 = binding.Password2.text.toString().trim()

            if (nickname.isEmpty()) {
                binding.Nickname.error = "Nickname is required!"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.EmailAddress.error = "Email is required!"
                return@setOnClickListener
            }
            if (password1.isEmpty()) {
                binding.Password1.error = "Password is required!"
                return@setOnClickListener
            }
            if (password2.isEmpty()) {
                binding.Password2.error = "Please confirm your password!"
                return@setOnClickListener
            }
            if (password1 != password2) {
                binding.Password2.error = "Passwords do not match!"
                return@setOnClickListener
            }

            // ✅ Now the function will handle navigation itself
            signup(nickname, email, password1)
        }

        // Toggle Password1 visibility
        binding.togglePassword1.setOnClickListener {
            isPasswordVisible1 = !isPasswordVisible1
            if (isPasswordVisible1) {
                binding.Password1.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.togglePassword1.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to eye-off icon
            } else {
                binding.Password1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.togglePassword1.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to eye-on icon
            }
            binding.Password1.setSelection(binding.Password1.text.length) // Keep cursor at the end
        }

        // Toggle Password2 visibility
        binding.togglePassword2.setOnClickListener {
            isPasswordVisible2 = !isPasswordVisible2
            if (isPasswordVisible2) {
                binding.Password2.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.togglePassword2.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to eye-off icon
            } else {
                binding.Password2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.togglePassword2.setImageResource(R.drawable.baseline_remove_red_eye_24) // Change to eye-on icon
            }
            binding.Password2.setSelection(binding.Password2.text.length) // Keep cursor at the end
        }

        // ✅ Other buttons remain unchanged
        binding.loginbtn1.setOnClickListener {
            val intent = Intent(this, LoginForm::class.java)
            startActivity(intent)
        }
    }
    private fun signup(username: String, email: String, password: String) {
        val signupReq = SignupReq(username = username, email = email, password = password, phoneNum = null)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.signup(signupReq)
                if (response.success) {
                    Toast.makeText(this@SignUp, "Registration successful!", Toast.LENGTH_SHORT).show()

                    // ✅ Navigate to LoginForm after successful signup
                    val intent = Intent(this@SignUp, LoginForm::class.java)
                    startActivity(intent)
                    finish() // ✅ Prevent going back to signup page
                } else {
                    Toast.makeText(this@SignUp, "Signup failed: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignUp, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
