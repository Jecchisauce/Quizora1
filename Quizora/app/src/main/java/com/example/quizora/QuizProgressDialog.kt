package com.example.quizora

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class QuizProgressDialog(
    private val onReturn: () -> Unit,
    private val onRetry: () -> Unit,
    private val onContinue: () -> Unit // New function for continue button
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnReturn = view.findViewById<Button>(R.id.btn_return)
        val btnRetry = view.findViewById<Button>(R.id.btn_retry)
        val btnContinue = view.findViewById<Button>(R.id.btn_continue) // Add Continue button

        // Handle Return (Save Progress)
        btnReturn.setOnClickListener {
            onReturn.invoke()
            dismiss()
        }

        // Handle Retry (Restart Quiz)
        btnRetry.setOnClickListener {
            onRetry.invoke()
            dismiss()
        }

        // Handle Continue (Resume Quiz)
        btnContinue.setOnClickListener {
            onContinue.invoke()
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.DialogTheme) // Apply theme if needed
    }
}
