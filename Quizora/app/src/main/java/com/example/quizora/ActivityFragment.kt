package com.example.quizora

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityFragment : Fragment(R.layout.fragment_activity) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.quiz_items)

        // Sample quiz items
        val quizList = listOf(
            QuizItem("Math Quiz", 10),
            QuizItem("Science Quiz", 15),
            QuizItem("History Quiz", 8),
            QuizItem("Geography Quiz", 12),
            QuizItem("English Quiz", 20),
            QuizItem("Programming Quiz", 25)
        )

        // Set layout manager (Grid with 2 columns)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = QuizAdapter(quizList)
    }
}
