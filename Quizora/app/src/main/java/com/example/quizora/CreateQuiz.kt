package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CreateQuiz : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_quiz)

        val backButton = findViewById<Button>(R.id.back_buttonC)
        val quizNameInput = findViewById<EditText>(R.id.quiz_name_input)
        val createQuizButton = findViewById<Button>(R.id.create_quiz_button)
        val quizLogo = findViewById<ImageView>(R.id.quiz_logo)

        // Remove the image from the ImageView
        quizLogo.setImageResource(0) // or quizLogo.setImageDrawable(null)

        // Prevent emojis in the EditText
        quizNameInput.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            for (index in start until end) {
                val type = Character.getType(source[index])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return@InputFilter "" // Return empty string to filter out the character
                }
            }
            null // Allow other characters
        })

        backButton.setOnClickListener {
            finish()
        }

        createQuizButton.setOnClickListener {
            val quizName = quizNameInput.text.toString().trim()

            if (quizName.isNotEmpty()) {
                val intent = Intent(this, QuizCreation::class.java) // Replace with your next activity
                intent.putExtra("quizName", quizName)
                startActivity(intent)
            } else {
                quizNameInput.error = "Quiz name is required"
            }
        }
    }
}