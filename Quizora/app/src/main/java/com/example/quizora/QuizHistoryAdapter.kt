package com.example.quizora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizHistoryAdapter(private val quizList: List<QuizHistory>) :
    RecyclerView.Adapter<QuizHistoryAdapter.QuizViewHolder>() {

    class QuizViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quizTitle: TextView = view.findViewById(R.id.historyQuizTitle)
        val quizAccuracy: TextView = view.findViewById(R.id.display_accuracy)
        val quizImage: ImageView = view.findViewById(R.id.historyQuizImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quiz_history, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.quizTitle.text = quiz.title
        holder.quizAccuracy.text = quiz.accuracy
        holder.quizImage.setImageResource(quiz.imageResId) // Set quiz image
    }

    override fun getItemCount(): Int = quizList.size
}
