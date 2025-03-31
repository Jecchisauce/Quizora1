package com.example.quizora

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizora.fucntions.Global
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private lateinit var tvTotalQuizzes: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvAverage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var quizHistoryAdapter: QuizHistoryAdapter
    private var quizHistoryList = mutableListOf<QuizHistory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI Elements
        tvUsername = view.findViewById(R.id.tvUsername)
        tvTotalQuizzes = view.findViewById(R.id.tvTotalQuizzes)
        tvAverage = view.findViewById(R.id.tvAverage)
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        val resetButton: Button = view.findViewById(R.id.btnResetStats)

        tvUsername.text = Global.USERNAME
        tvTotalQuizzes.text = Global.TOTAL_QUIZZES.toString()

        // Setup RecyclerView (Horizontal Scrolling)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        quizHistoryAdapter = QuizHistoryAdapter(quizHistoryList)
        recyclerView.adapter = quizHistoryAdapter

        // Load User Stats & Quiz History
        updateUserStats()
        loadQuizHistory()




//        // 🟢 Add Sample Data (for Display Purposes)
//        if (quizHistoryList.isEmpty()) {
//            quizHistoryList.add(QuizHistory("Math Quiz", "Accuracy: 75%", R.drawable.ma_th))
//            quizHistoryAdapter.notifyDataSetChanged()
//        }


        // Reset Data on Button Click
        resetButton.setOnClickListener {
            resetQuizData()
        }
    }


    private fun updateUserStats() {
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
        val totalQuizzes = sharedPref.getInt("TOTAL_QUIZZES", 0)
        val totalScore = sharedPref.getInt("TOTAL_SCORE", 0)

        // 🔹 Automatically determine max score based on recorded quiz history
        val totalPossibleScore = totalQuizzes * (sharedPref.getInt("MAX_SCORE_PER_QUIZ", 10)) // Default 10 per quiz

        // 🔥 Ensure accuracy calculation is valid
        val totalQuestions = totalPossibleScore / 10 // Assuming 10 points per question
        val mistakes = totalQuestions - (totalScore / 10) // Questions answered incorrectly

// Apply a diminishing effect for mistakes
        val mistakePenalty = mistakes * 0.33 // Reduce based on number of mistakes

// Calculate accuracy and ensure it never goes below 0%
        val accuracy = if (totalPossibleScore > 0) {
            ((totalScore.toDouble() / totalPossibleScore * 100) - mistakePenalty).toInt().coerceIn(0, 100)
        } else {
            100 // Default to 100% when no quizzes are played
        }

// Update UI
        tvAverage.text = "Accuracy: $accuracy%"


        // Update UI
        tvTotalQuizzes.text = totalQuizzes.toString()
        tvAverage.text = "$accuracy%"

        Log.d("UserStats", "Total Quizzes: $totalQuizzes, Total Score: $totalScore, Accuracy: $accuracy%")
    }

    private fun loadQuizHistory() {
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPref.getString("QUIZ_HISTORY", "[]")
        Log.d("QuizHistory", "Loaded JSON: $json")

        val type = object : TypeToken<List<QuizHistory>>() {}.type
        val historyList: List<QuizHistory> = gson.fromJson(json, type) ?: emptyList()

        quizHistoryList.clear()
        quizHistoryList.addAll(historyList)

        requireActivity().runOnUiThread {
            quizHistoryAdapter.notifyDataSetChanged()
        }

        Log.d("QuizHistory", "List Updated: ${quizHistoryList.size} items")
    }

    private fun resetQuizData() {
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply() // Reset all stored values

        // Reset UI
        tvTotalQuizzes.text = "0"
        tvAverage.text = "0%"

        // Clear history list
        quizHistoryList.clear()
        quizHistoryAdapter.notifyDataSetChanged()

        Log.d("QuizReset", "Quiz stats reset successfully!")
    }
}
