package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.api.RetrofitClient
import com.example.quizora.models.RegisterRequest
import com.example.quizora.models.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        usernameEditText = findViewById(R.id.Nickname)
        emailEditText = findViewById(R.id.EmailAddress)
        passwordEditText = findViewById(R.id.Password1)
        confirmPasswordEditText = findViewById(R.id.Password2)
        signupButton = findViewById(R.id.Signup)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInput(username, email, password, confirmPassword)) {
                registerUser(username, email, password, confirmPassword)
            }
        }
    }

    private fun validateInput(username: String, email: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerUser(username: String, email: String, password: String, confirmPassword: String) {
        val request = RegisterRequest(username, email, password, confirmPassword)

        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@SignupActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignupActivity, response.body()?.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("SignupError", "Error: ${t.message}")
                Toast.makeText(this@SignupActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
