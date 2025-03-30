package com.example.quizora

import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityFragment : Fragment(R.layout.fragment_activity) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.quiz_items)
        val addQuizButton: Button = view.findViewById(R.id.AddQuiz)

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
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = QuizAdapter(quizList)

        // Set click listener for AddQuiz button
        addQuizButton.setOnClickListener {
            showCreateQuizFragment()
        }
    }

    private fun showCreateQuizFragment() {
        // Hide the bottom navigation
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav1)?.visibility = View.GONE

        // Create an instance of the CreateQuizFragment
        val createQuizFragment = CreateQuizFragment()

        // Replace the current fragment with the CreateQuizFragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, createQuizFragment) // Replace fragment_container with your container ID
            .addToBackStack(null) // Add to back stack if you want to navigate back
            .commit()

        // Prevent emojis and non-text characters in the quiz_name_input EditText (inside CreateQuizFragment)
        createQuizFragment.arguments = Bundle().apply {
            putString("filter_input", "true") // Pass a flag to the fragment
        }
    }


    // CreateQuizFragment class (FUNCTION)
    class CreateQuizFragment : Fragment(R.layout.fragment_create_quiz) {

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val backButton = view.findViewById<Button>(R.id.back_buttonC)
            val quizNameInput = view.findViewById<EditText>(R.id.quiz_name_input)
            val createQuizButton = view.findViewById<Button>(R.id.create_quiz_button)
            val quizLogo = view.findViewById<ImageView>(R.id.quiz_logo)

            // Remove the image from the ImageView
            quizLogo.setImageResource(0) // Or quizLogo.setImageDrawable(null)

            // Prevent emojis and limit input to 12 characters and single space
            quizNameInput.filters = arrayOf(
                InputFilter.LengthFilter(12), // Limit to 12 characters
                InputFilter { source, start, end, dest, dstart, dend ->
                    for (index in start until end) {
                        val type = Character.getType(source[index])
                        if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                            return@InputFilter "" // Filter out emojis and symbols
                        }
                        if (source[index] == '\n') {
                            return@InputFilter "" // Filter out newline characters
                        }
                    }

                    // Allow only one space between words
                    if (source.toString().contains("  ")) {
                        return@InputFilter dest.subSequence(dstart, dend) // Prevent double spaces
                    }

                    null // Allow other characters
                }
            )

            backButton.setOnClickListener {
                // Show the bottom navigation
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav1)?.visibility = View.VISIBLE

                // Navigate back to the ActivityFragment
                requireActivity().supportFragmentManager.popBackStack()
            }

            createQuizButton.setOnClickListener {
                val quizName = quizNameInput.text.toString().trim()

                if (quizName.isNotEmpty()) {
                    // Quiz name is valid, proceed to the LoadingFragment
                    val loadingFragment = LoadingFragment()
                    val bundle = Bundle()
                    bundle.putString("quizName", quizName)
                    loadingFragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, loadingFragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Quiz name is empty, show an error message
                    quizNameInput.error = "Quiz name is required"
                }
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Override the back press behavior within the fragment
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Show the bottom navigation
                    activity?.findViewById<BottomNavigationView>(R.id.bottom_nav1)?.visibility = View.VISIBLE

                    // Navigate back to the ActivityFragment
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        }
    }
}

class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextView and set the text
        val loadingText: TextView = view.findViewById(R.id.loading_text)
        loadingText.text = "Generating your quiz layout"

        // Simulate loading (replace with your actual loading logic)
        Handler().postDelayed({
            // Replace this with your navigation logic (e.g., replace fragment, navigate to activity)
            // Example: Replace with another fragment
            val nextFragment = QuizCreation() // Replace with your next fragment
            val bundle = arguments
            nextFragment.arguments = bundle;
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, nextFragment) // Replace with your container ID
                .commit()
        }, 1000) // Delay for 3 seconds (adjust as needed)
    }
}

class QuizCreationFragment : Fragment(R.layout.fragment_quiz_creation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.back_button)
        val quizTitle = view.findViewById<TextView>(R.id.QuizTitle)
        val quizName = arguments?.getString("quizName")

        if (quizName != null) {
            quizTitle.text = quizName
        } else {
            quizTitle.text = "Quiz" // Default title if quizName is null
        }

        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }
}