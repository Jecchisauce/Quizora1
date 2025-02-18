package com.example.quizora

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizStatusText = view.findViewById<TextView>(R.id.quiz_status_text1)

    // Example logic to check if the quiz is answered
        val isAnswered = false // Replace with actual logic

    // Update text dynamically
        if (isAnswered) {
            quizStatusText.text = "Completed"
            quizStatusText.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
        } else {
            quizStatusText.text = "Answer Quiz"
            quizStatusText.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))
        }


        // Get references to UI elements
        val searchBar = view.findViewById<EditText>(R.id.search_bar)
        val clearButton = view.findViewById<ImageView>(R.id.clear_button)

        // Add TextWatcher to search bar to prevent unnecessary spaces
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val trimmedText = s.toString().trim()
                if (searchBar.text.toString() != trimmedText) {
                    searchBar.setText(trimmedText)
                    searchBar.setSelection(trimmedText.length) // Keep cursor at the end
                }
            }
        })

        searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                // Trim spaces and ensure only a single-line input
                val query = searchBar.text.toString().trim()

                if (query.isNotEmpty()) {
                    performSearch(query)
                } else {
                    Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show()
                }

                hideKeyboard(searchBar)
                true
            } else {
                false
            }
        }

        // Set click listener to clear button
        clearButton.setOnClickListener {
            searchBar.text.clear()
        }
    }

    private fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            // Here you can add logic to search within the quiz items
            Toast.makeText(requireContext(), "Searching for: $query", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


