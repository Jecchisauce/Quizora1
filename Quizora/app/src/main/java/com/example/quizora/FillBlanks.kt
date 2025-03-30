package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FillBlanks : Fragment() {

    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var timeLimitButton: Button
    private lateinit var pointsButton: Button
    private lateinit var addQuestionButton: Button
    private lateinit var backButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fill_blanks, container, false)

        questionEditText = view.findViewById(R.id.question_text)
        answerEditText = view.findViewById(R.id.answer_text)
        timeLimitButton = view.findViewById(R.id.time_limit)
        pointsButton = view.findViewById(R.id.points)
        addQuestionButton = view.findViewById(R.id.add_question_button)
        backButton = view.findViewById(R.id.back_button)

        // Set up listeners initially
        setupListeners()

        return view
    }

    private fun setupListeners() {
        timeLimitButton.setOnClickListener {
            // Handle time limit selection
            Toast.makeText(context, "Time limit selected", Toast.LENGTH_SHORT).show()
            // Add your time limit selection logic here
        }

        pointsButton.setOnClickListener {
            // Handle points selection
            Toast.makeText(context, "Points selected", Toast.LENGTH_SHORT).show()
            // Add your points selection logic here
        }

        addQuestionButton.setOnClickListener {
            addQuestion()
        }

        backButton.setOnClickListener {
            navigateToQuizCreation()
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-establish the click listeners when the fragment is resumed
        setupListeners()
    }

    private fun addQuestion() {
        val question = questionEditText.text.toString().trim()
        val answer = answerEditText.text.toString().trim()

        if (question.isNotEmpty() && answer.isNotEmpty()) {
            // Add your logic to save the question and answer
            Toast.makeText(context, "Question added: $question, Answer: $answer", Toast.LENGTH_SHORT).show()
            // Example: Save to a database or send to an API

            // Clear the EditText fields
            questionEditText.text.clear()
            answerEditText.text.clear()
        } else {
            Toast.makeText(context, "Please enter both question and answer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToQuizCreation() {
        val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, QuizCreationFragment()) // Replace QuizCreationFragment with your quiz creation fragment
        fragmentTransaction.addToBackStack(null) // Optional: Add to back stack
        fragmentTransaction.commit()
    }
}