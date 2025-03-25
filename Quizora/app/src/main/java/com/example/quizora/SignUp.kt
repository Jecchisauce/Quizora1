package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizora.api.RetrofitClient
import com.example.quizora.databinding.ActivitySignUpBinding
import com.example.quizora.models.RegisterRequest
import com.example.quizora.models.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var isPasswordVisible1 = false
    private var isPasswordVisible2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Signup button logic
        binding.Signup.setOnClickListener {
            val nickname = binding.Nickname.text.toString().trim()
            val email = binding.EmailAddress.text.toString().trim()
            val password1 = binding.Password1.text.toString().trim()
            val password2 = binding.Password2.text.toString().trim()

            if (validateInput(nickname, email, password1, password2)) {
                registerUser(nickname, email, password1)
            }
        }

        // Toggle Password1 visibility
        binding.togglePassword1.setOnClickListener {
            isPasswordVisible1 = !isPasswordVisible1
            togglePasswordVisibility(binding.Password1, binding.togglePassword1, isPasswordVisible1)
        }

        // Toggle Password2 visibility
        binding.togglePassword2.setOnClickListener {
            isPasswordVisible2 = !isPasswordVisible2
            togglePasswordVisibility(binding.Password2, binding.togglePassword2, isPasswordVisible2)
        }

        binding.loginbtn1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Validate user input
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

    // Register User API Call
    private fun registerUser(nickname: String, email: String, password: String) {
        val request = RegisterRequest(nickname, email, password, password)

        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@SignUp, "Registration successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUp, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignUp, response.body()?.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("SignupError", "Error: ${t.message}")
                Toast.makeText(this@SignUp, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Toggle password visibility
    private fun togglePasswordVisibility(passwordField: EditText, toggleButton: ImageButton, isVisible: Boolean) {
        if (isVisible) {
            passwordField.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordField.transformationMethod = null
            toggleButton.setImageResource(R.drawable.baseline_remove_red_eye_24)
        } else {
            passwordField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordField.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.baseline_visibility_off_24)
        }
        passwordField.setSelection(passwordField.text.length)
    }
}