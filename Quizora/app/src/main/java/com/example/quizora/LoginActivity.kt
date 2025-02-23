package com.example.quizora

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.api.RetrofitClient
import com.example.quizora.models.LoginResponse
import kotlinx.coroutines.*
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.EmailAddress)
        passwordEditText = findViewById(R.id.Password)
        loginButton = findViewById(R.id.Signin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.loginUser(username, password)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val result: LoginResponse = response.body()!!
                        showToast(result.message)

                        if (result.success) {
                            startActivity(Intent(this@LoginActivity, HomeFragment::class.java))
                            finish()
                        }
                    } else {
                        showToast("Login failed: ${response.code()}")
                    }
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
