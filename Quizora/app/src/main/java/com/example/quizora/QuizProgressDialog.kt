package com.example.quizora

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class QuizProgressDialog(
    private val onReturn: () -> Unit,
    private val onRetry: () -> Unit,
    private val onContinue: () -> Unit
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
        val btnContinue = view.findViewById<Button>(R.id.btn_continue)

        // Set the background selector for all buttons
        btnReturn.setBackgroundResource(R.drawable.button_selector)
        btnRetry.setBackgroundResource(R.drawable.button_selector)
        btnContinue.setBackgroundResource(R.drawable.button_selector)

        // Handle clicks
        btnReturn.setOnClickListener {
            onReturn.invoke()
            dismiss()
        }

        btnRetry.setOnClickListener {
            onRetry.invoke()
            dismiss()
        }

        btnContinue.setOnClickListener {
            onContinue.invoke()
            dismiss()
        }

        // Prevent back button from dismissing the dialog
        dialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                onContinue.invoke() // Continue the quiz instead of closing the dialog
                dismiss()
                true
            } else {
                false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.DialogTheme).apply {
            setCancelable(false) // Prevents dismissing by tapping outside
            setCanceledOnTouchOutside(false) // Prevents dismissing by touching outside the dialog
        }
    }
}
