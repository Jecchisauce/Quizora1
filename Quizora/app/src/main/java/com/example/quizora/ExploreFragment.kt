package com.example.quizora

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizStatusText = view.findViewById<TextView>(R.id.quiz_status_text1)
        val quiz1 = view.findViewById<View>(R.id.Quiz1) // Get reference to Quiz1 layout

        // Show quiz details when clicked
        quiz1.setOnClickListener {
            showQuizDialog()
        }

        // Example logic to check if the quiz is answered
        val isAnswered = false // Replace with actual logic

        // Update text dynamically
        if (isAnswered) {
            quizStatusText.text = "Completed"
            quizStatusText.setTextColor(
                ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
            )
        } else {
            quizStatusText.text = "Answer Quiz"
            quizStatusText.setTextColor(
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark)
            )
        }

        // Get references to UI elements
        val searchBar = view.findViewById<EditText>(R.id.search_bar)
        val clearButton = view.findViewById<ImageView>(R.id.clear_button)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredText = s.toString().replace(Regex("\\s{2,}"), " ") // Replace multiple spaces with a single space
                if (searchBar.text.toString() != filteredText) {
                    searchBar.setText(filteredText)
                    searchBar.setSelection(filteredText.length) // Keep cursor at the end
                }
            }
        })

        searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val query = searchBar.text.toString().trim()

                if (query.isNotEmpty()) {
                    performSearch(query)
                } else {
                    Toast.makeText(requireContext(), "Search bar is Empty", Toast.LENGTH_SHORT).show()
                }

                hideKeyboard(searchBar)
                true
            } else {
                false
            }
        }

        clearButton.setOnClickListener {
            searchBar.text.clear()
        }
    }

    private fun showQuizDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_quiz_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Hide background of the dialog
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val quizTitle: TextView = dialogView.findViewById(R.id.quiz_title)
        val quizDescription: TextView = dialogView.findViewById(R.id.quiz_description)
        val questionCount: TextView = dialogView.findViewById(R.id.quiz_question_count)
        val cancelButton: Button = dialogView.findViewById(R.id.cancel_button)
        val startButton: Button = dialogView.findViewById(R.id.start_quiz_button)

        quizTitle.text = "Math Quiz"
        quizDescription.text = "Basic algebra and equations."
        questionCount.text = "1 Question"

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        startButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AnswerMath())
                .addToBackStack(null)
                .commit()
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            Toast.makeText(requireContext(), "Searching for: $query", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
