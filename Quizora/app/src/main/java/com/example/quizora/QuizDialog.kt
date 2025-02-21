package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class QuizDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_quiz_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView: TextView = view.findViewById(R.id.quiz_title)
        val descriptionTextView: TextView = view.findViewById(R.id.quiz_description)
        val questionCountTextView: TextView = view.findViewById(R.id.quiz_question_count)
        val cancelButton: Button = view.findViewById(R.id.cancel_button)
        val startButton: Button = view.findViewById(R.id.start_quiz_button)

        cancelButton.setOnClickListener {
            dismiss()
        }

        startButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        fun newInstance(): QuizDialog {
            return QuizDialog()
        }
    }
}
