package com.example.quizora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class QuizHistory(val title: String, val score: String)

class QuizHistoryAdapter(private val quizList: List<QuizHistory>) :
    RecyclerView.Adapter<QuizHistoryAdapter.QuizViewHolder>() {

    class QuizViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quizTitle: TextView = view.findViewById(R.id.historyQuizTitle)
        val quizScore: TextView = view.findViewById(R.id.historyQuizScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quiz_history, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.quizTitle.text = quiz.title
        holder.quizScore.text = quiz.score
    }

    override fun getItemCount(): Int = quizList.size
}
