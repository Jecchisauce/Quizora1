package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizora.api.RetrofitClient
import com.example.quizora.databinding.ActivityLoginFormBinding
import com.example.quizora.models.LoginRequest
import com.example.quizora.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginForm : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets (for proper layout handling on modern devices)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Login Button Click
        binding.Signin.setOnClickListener {
            val username = binding.EmailAddress.text.toString().trim()
            val password = binding.Password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(username, password)
            }
        }

        // Sign Up Button Click
        binding.Signupbtn.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        // Forgot Password Button Click
        binding.forgotbtn.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
    }

    private fun loginUser(username: String, password: String) {
        val request = LoginRequest(username, password)

        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@LoginForm, "Login successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginForm, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginForm, loginResponse?.message ?: "Login failed (no message)", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginForm, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginForm, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}
