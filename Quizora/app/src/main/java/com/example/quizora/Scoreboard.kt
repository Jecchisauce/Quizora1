package com.example.quizora

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class ScoreboardFragment : Fragment() {

    private lateinit var scoreDisplay: TextView
    private lateinit var highScoreDisplay: TextView
    private lateinit var playAgainButton: Button
    private lateinit var exitButton: Button

    private var score: Int = 0
    private var highScore: Int = 0
    private val totalQuestions: Int = 10 //  number of questions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve quiz score from arguments
        score = arguments?.getInt("QUIZ_SCORE") ?: 0

        // Hide bottom navigation bar
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE

        // Disable back button
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Do nothing (Back button disabled)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_scoreboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Hide bottom navigation again (in case it's shown)
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE

        // Initialize UI elements
        scoreDisplay = view.findViewById(R.id.score_display)
        highScoreDisplay = view.findViewById(R.id.accuracy_display)
        playAgainButton = view.findViewById(R.id.play_again_btn)
        exitButton = view.findViewById(R.id.exit_btn)

        // Retrieve and update high score
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
        highScore = sharedPref.getInt("HIGH_SCORE", 0)

        if (score > highScore) {
            highScore = score
            sharedPref.edit().putInt("HIGH_SCORE", highScore).apply()
        }



        // Update total quizzes played and total score
        val totalQuizzes = sharedPref.getInt("TOTAL_QUIZZES", 0) + 1
        val totalScore = sharedPref.getInt("TOTAL_SCORE", 0) + score

        sharedPref.edit()
            .putInt("TOTAL_QUIZZES", totalQuizzes)
            .putInt("TOTAL_SCORE", totalScore)
            .apply()



        // Calculate and display accuracy
        val accuracy = if (totalQuestions > 0) (score.toDouble() / totalQuestions * 100).toInt() else 0
        highScoreDisplay.text = "Accuracy: $accuracy%"

        // Display scores
        scoreDisplay.text = "Your Score: $score"

        // Play Again Button - Restart the quiz
        playAgainButton.setOnClickListener {
            restartQuiz()
        }

        // Exit Button - Redirect to another fragment
        exitButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNav = requireActivity().findViewById<View>(R.id.bottom_nav1)
        bottomNav?.postDelayed({
            bottomNav.visibility = View.VISIBLE
        }, 100)
    }

    private fun restartQuiz() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AnswerMath()) // Replace with your quiz fragment
            .commit()
    }



    companion object {
        fun newInstance(score: Int): ScoreboardFragment {
            return ScoreboardFragment().apply {
                arguments = Bundle().apply {
                    putInt("QUIZ_SCORE", score)
                }
            }
        }
    }
}


