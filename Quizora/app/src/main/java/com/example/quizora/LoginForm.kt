package com.example.quizora

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.quizora.databinding.ActivityLoginFormBinding
import com.example.quizora.fucntions.Global
import com.example.quizora.fucntions.LoginReq
import com.example.quizora.fucntions.RetrofitClient
import kotlinx.coroutines.launch

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

        binding.Password.filters = arrayOf(blockEmojis())


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
            }
            if (password.isEmpty()) {
                binding.Password.error = "Password is required!"
            }

            checkUserInDatabase(email, password)
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
    private fun checkUserInDatabase(email: String, password: String) {
        val loginReq = LoginReq(email = email, password = password)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(loginReq)
                Log.d("LoginDebug", "Response: $response") // Debugging line

                if (response.success) {
                    Global.LOGGED = true
                    Global.USERNAME = response.user?.username
                    Global.ID = response.user?.id
                    Global.ACCESS = response.user?.access

                    Toast.makeText(this@LoginForm, "Login successful!", Toast.LENGTH_SHORT).show()

                    // ✅ Move to MainActivity after success
                    val intent = Intent(this@LoginForm, MainActivity::class.java)
                    startActivity(intent)
                    finish() // ✅ Prevent user from returning to login

                } else {
                    Log.e("LoginDebug", "Login failed: $response")
                    Toast.makeText(this@LoginForm, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    binding.EmailAddress.error = "Invalid credentials"
                    binding.Password.error = "Invalid credentials"
                }

            } catch (e: Exception) {
                Log.e("LoginDebug", "Error: ${e.message}")
                Toast.makeText(this@LoginForm, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun blockEmojis(): InputFilter {
        return object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                if (source == null) return null

                for (i in start until end) {
                    val type = Character.getType(source[i])
                    if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                        return ""
                    }
                }
                return null
            }
        }
    }
}
