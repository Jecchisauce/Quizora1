package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.api.RetrofitClient
import com.example.quizora.models.RegisterResponse
import com.example.quizora.models.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        usernameEditText = findViewById(R.id.EmailAddress)
        passwordEditText = findViewById(R.id.Password)
        loginButton = findViewById(R.id.Signin)

        loginButton.setOnClickListener {
            val request = UserRequest(usernameEditText.text.toString(), null, passwordEditText.text.toString())

            RetrofitClient.instance.loginUser(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
