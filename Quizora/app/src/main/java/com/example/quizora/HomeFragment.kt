package com.example.quizora

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private lateinit var tvTotalQuizzes: TextView
    private lateinit var tvAverage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var quizHistoryAdapter: QuizHistoryAdapter
    private var quizHistoryList = mutableListOf<QuizHistory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Reference RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewHistory)

        // Set layout manager (Horizontal scrolling)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Initialize adapter with empty list (it will be updated dynamically)
        quizHistoryAdapter = QuizHistoryAdapter(quizHistoryList)
        recyclerView.adapter = quizHistoryAdapter

        // Load quiz history
        loadQuizHistory()

        quizHistoryList.add(QuizHistory("Math Quiz", "85%", R.drawable.ma_th))
        quizHistoryList.add(QuizHistory("Science Quiz", "90%", R.drawable.scie_nce))


        quizHistoryAdapter.notifyDataSetChanged()

    }


    private fun updateUserStats() {
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)

        val totalQuizzes = sharedPref.getInt("TOTAL_QUIZZES", 0)
        val totalScore = sharedPref.getInt("TOTAL_SCORE", 0)
        val totalPossibleScore = totalQuizzes * 10 // Assuming each quiz has 10 questions

        // Calculate accuracy percentage, ensuring it doesn't exceed 100%
        val accuracy = if (totalQuizzes > 0) {
            minOf((totalScore.toDouble() / totalPossibleScore * 100).toInt(), 100)
        } else {
            100 // Start at 100% if no quizzes were played
        }

        // Update UI
        tvTotalQuizzes.text = totalQuizzes.toString()
        tvAverage.text = "$accuracy%"
    }

    private fun loadQuizHistory() {
        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPref.getString("QUIZ_HISTORY", "[]")
        Log.d("QuizHistory", "Loaded JSON: $json") // ✅ Log data to check if it exists

        val type = object : TypeToken<List<QuizHistory>>() {}.type
        val historyList: List<QuizHistory> = gson.fromJson(json, type) ?: emptyList()

        quizHistoryList.clear()
        quizHistoryList.addAll(historyList)
        quizHistoryAdapter.notifyDataSetChanged()
    }

}
