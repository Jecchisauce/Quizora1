package com.example.yourpackage // Replace with your actual package name

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.quizora.ActivityFragment
import com.example.quizora.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmptyQuizDialogFragment : DialogFragment() {

    private var onContinueEditingListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_quiz_dialog, container, false) // Replace with your layout name
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Remove title bar
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposeButton = view.findViewById<Button>(R.id.btn_dispose)
        val addQuestionsButton = view.findViewById<Button>(R.id.btn_add_questions)

        disposeButton.setOnClickListener {
            dismiss() // Dismiss the dialog
            // Redirect to ActivityFragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ActivityFragment())
                .commit()

            // Re-appear the bottom navigation
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav1)?.visibility = View.VISIBLE
        }

        addQuestionsButton.setOnClickListener {
            dismiss()
            onContinueEditingListener?.invoke() // Notify the listener
        }
    }

    fun setOnContinueEditingListener(listener: () -> Unit) {
        onContinueEditingListener = listener
    }

    companion object {
        const val TAG = "EmptyQuizDialog"
    }
}