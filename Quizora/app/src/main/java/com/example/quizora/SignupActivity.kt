package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.api.RetrofitClient
import com.example.quizora.models.RegisterResponse
import kotlinx.coroutines.*
import retrofit2.HttpException

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

            if (password != confirmPassword) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }

            registerUser(username, email, password)
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.registerUser(username, email, password)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val result: RegisterResponse = response.body()!!
                        showToast(result.message)

                        if (result.success) {
                            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                            finish()
                        }
                    } else {
                        showToast("Signup failed: ${response.code()}")
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    showToast("Network error: ${e.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.localizedMessage}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
    }
}
