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

        val quizData = listOf(
            Triple(R.id.Quiz1, "Math Quiz", R.drawable.ma_th) to Pair("Basic algebra and equations.", AnswerMath()),
            Triple(R.id.Quiz2, "Science Quiz", R.drawable.scie_nce) to Pair("Discover facts about nature.", AnswerScience()),
            Triple(R.id.Quiz3, "History Quiz", R.drawable.his_tory) to Pair("Test your history knowledge.", AnswerHistory()),
            Triple(R.id.Quiz4, "English Quiz", R.drawable.en_glish) to Pair("Learn fun language facts.", AnswerEnglish())
        )

        for ((quizInfo, details) in quizData) {
            val (quizId, title, imageResId) = quizInfo
            val (description, fragment) = details
            val quizLayout = view.findViewById<View>(quizId)

            quizLayout.setOnClickListener {
                showQuizDialog(title, description, "10 Questions", imageResId, fragment)
            }
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

    @SuppressLint("SetTextI18n")
    private fun showQuizDialog(title: String, description: String, questionCount: String, imageResId: Int, fragment: Fragment) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_quiz_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Hide background of the dialog
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // References to UI elements
        val quizImage: ImageView = dialogView.findViewById(R.id.quiz_image)
        val quizTitle: TextView = dialogView.findViewById(R.id.quiz_title)
        val quizDescription: TextView = dialogView.findViewById(R.id.quiz_description)
        val questionCountText: TextView = dialogView.findViewById(R.id.quiz_question_count)
        val cancelButton: Button = dialogView.findViewById(R.id.cancel_button)
        val startButton: Button = dialogView.findViewById(R.id.start_quiz_button)

        // Set dynamic quiz content
        quizTitle.text = title
        quizDescription.text = description
        questionCountText.text = questionCount
        quizImage.setImageResource(imageResId)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        startButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
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
