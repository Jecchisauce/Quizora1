package com.example.quizora

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
    private lateinit var answerA: Button
    private lateinit var answerB: Button
    private lateinit var answerC: Button
    private lateinit var answerD: Button
    private var timeLeft = 100  // Timer starts at 100%

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
        questionText = view.findViewById(R.id.question_text)
        answerA = view.findViewById(R.id.answer_a)
        answerB = view.findViewById(R.id.answer_b)
        answerC = view.findViewById(R.id.answer_c)
        answerD = view.findViewById(R.id.answer_d)

        // Start Timer
        startTimer()

        // Answer Click Listeners
        answerA.setOnClickListener { checkAnswer("A") }
        answerB.setOnClickListener { checkAnswer("B") }
        answerC.setOnClickListener { checkAnswer("C") }
        answerD.setOnClickListener { checkAnswer("D") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show the Bottom Navigation Bar again when leaving AnswerMath
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.VISIBLE
    }


    private fun startTimer() {
        val timer = object : CountDownTimer(10000, 100) { // 10 seconds
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 100).toInt()
                timerProgressBar.progress = timeLeft
            }

            override fun onFinish() {
                timerProgressBar.progress = 0
                Toast.makeText(requireContext(), "Time's up!", Toast.LENGTH_SHORT).show()
            }
        }
        timer.start()
    }

    private fun checkAnswer(selected: String) {
        val correctAnswer = "A"  // Example correct answer
        if (selected == correctAnswer) {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong answer!", Toast.LENGTH_SHORT).show()
        }
    }
}