//for now it stops because you have answered

package com.example.quizora

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class AnswerMath : Fragment() {
    private lateinit var timerProgressBar: ProgressBar
    private lateinit var questionText: TextView
    private lateinit var scoreboard: TextView  // Scoreboard Reference
    private lateinit var answerA: Button
    private lateinit var answerB: Button
    private lateinit var answerC: Button
    private lateinit var answerD: Button
    private var score = 0  // Score Counter
    private var timeLeft = 100  // Timer starts at 100%
    private var isAnswered = false // Track if the user has answered
    private var timer: CountDownTimer? = null // Store timer reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_answer_math, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the Bottom Navigation Bar when AnswerMath is opened
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE

        // Initialize UI Elements
        timerProgressBar = view.findViewById(R.id.timer_progress)
        scoreboard = view.findViewById(R.id.scoreboard)  // Initialize Scoreboard
        questionText = view.findViewById(R.id.question_text)
        answerA = view.findViewById(R.id.answer_a)
        answerB = view.findViewById(R.id.answer_b)
        answerC = view.findViewById(R.id.answer_c)
        answerD = view.findViewById(R.id.answer_d)

        // Initialize Back Button
        val backButton = view.findViewById<Button>(R.id.Math_backbtn)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack() // Navigate back to the previous fragment
        }

        // Start Timer
        startTimer()

        // Answer Listeners
        answerA.setOnClickListener { checkAnswer(answerA, "A") }
        answerB.setOnClickListener { checkAnswer(answerB, "B") }
        answerC.setOnClickListener { checkAnswer(answerC, "C") }
        answerD.setOnClickListener { checkAnswer(answerD, "D") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show the Bottom Navigation Bar again when leaving AnswerMath
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.VISIBLE
    }

    private fun startTimer() {
        timer = object : CountDownTimer(15000, 150) { // 15 seconds countdown
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 150).toInt()
                timerProgressBar.progress = timeLeft
            }

            override fun onFinish() {
                timerProgressBar.progress = 0

                // If timer is up and no answer is selected
                if (!isAnswered) {
                    Toast.makeText(requireContext(), "Time is up", Toast.LENGTH_SHORT).show()
                }

                // Disable all buttons after timeout
                disableAllButtons()
            }
        }
        timer?.start()
    }

    private fun checkAnswer(selectedButton: Button, selectedAnswer: String) {
        if (isAnswered) return // Prevent multiple selections

        isAnswered = true // Mark that the user has answered
        timer?.cancel() // Stop the timer immediately

        val correctAnswer = "A"  // Correct answer ID


        if (selectedAnswer == correctAnswer) {
            selectedButton.setBackgroundResource(R.drawable.correct_answer)  // Green background
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            updateScore()  // Increment score
        } else {
            selectedButton.setBackgroundResource(R.drawable.wrong_answer)  // Red background
            Toast.makeText(requireContext(), "Wrong answer!", Toast.LENGTH_SHORT).show()
        }

        // Disable all buttons after answering
        disableAllButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun updateScore() {
        score += 1  // Increase score by 1 point
        scoreboard.text = "SCORE: $score"  // Update scoreboard UI
    }

    private fun disableAllButtons() {
        answerA.isEnabled = false
        answerB.isEnabled = false
        answerC.isEnabled = false
        answerD.isEnabled = false
    }
}

