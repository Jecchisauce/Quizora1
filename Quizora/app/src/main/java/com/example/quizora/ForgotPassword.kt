package com.example.quizora

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate to ForgotPassword2
        findViewById<Button>(R.id.btnSend).setOnClickListener {
            val intent = Intent(this, ForgotPassword2::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, LoginForm::class.java)
            startActivity(intent)
            finish() // This closes the current activity

        }
    }
}
