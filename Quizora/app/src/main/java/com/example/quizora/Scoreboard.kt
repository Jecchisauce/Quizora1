package com.example.quizora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScoreboardFragment : Fragment() {

    private lateinit var scoreDisplay: TextView
    private lateinit var highScoreDisplay: TextView
    private lateinit var playAgainButton: Button
    private lateinit var exitButton: Button

    private var score: Int = 0
    private var highScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        score = arguments?.getInt("QUIZ_SCORE") ?: 0
        // Hide bottom navigation bar
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_scoreboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide bottom navigation bar
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE

        scoreDisplay = view.findViewById(R.id.score_display)
        highScoreDisplay = view.findViewById(R.id.high_score_display)
        playAgainButton = view.findViewById(R.id.play_again_btn)
        exitButton = view.findViewById(R.id.exit_btn)

        // Retrieve high score from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", 0)
        highScore = sharedPref.getInt("HIGH_SCORE", 0)

        // Update high score if the new score is higher
        if (score > highScore) {
            highScore = score
            sharedPref.edit().putInt("HIGH_SCORE", highScore).apply()
        }

        // Display scores
        scoreDisplay.text = "Your Score: $score"
        highScoreDisplay.text = "Your High Score: $highScore"

        // Play Again Button - Restart the quiz
        playAgainButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Exit Button - Redirect to another fragment
        exitButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExploreFragment()) // Replace with your actual fragment
                .commit()
        }
    }
    override fun onResume() {
        super.onResume()
        // Hide bottom navigation bar
        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNav = requireActivity().findViewById<View>(R.id.bottom_nav1)
        bottomNav?.postDelayed({
            bottomNav.visibility = View.VISIBLE
        }, 100)
    }


    // Removed onDestroyView()

    companion object {
        fun newInstance(score: Int): ScoreboardFragment {
            val fragment = ScoreboardFragment()
            val args = Bundle()
            args.putInt("QUIZ_SCORE", score)
            fragment.arguments = args
            return fragment
        }
    }
}