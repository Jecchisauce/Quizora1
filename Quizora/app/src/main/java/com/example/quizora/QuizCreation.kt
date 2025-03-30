package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.yourpackage.EmptyQuizDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuizCreation : Fragment() {

    private lateinit var createQuestionButton: Button
    private lateinit var backButton: Button
    private lateinit var quizTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createQuestionButton = view.findViewById(R.id.create_question_button)
        backButton = view.findViewById(R.id.back_button)
        quizTitle = view.findViewById(R.id.QuizTitle)

        val quizName = arguments?.getString("quizName")

        if (quizName != null) {
            quizTitle.text = quizName
        } else {
            quizTitle.text = "Quiz"
        }

        backButton.setOnClickListener {
            showConfirmationDialog(childFragmentManager)
        }

        // Set the click listener initially
        createQuestionButton.setOnClickListener {
            showSelectQuestionTypeBottomSheet(childFragmentManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Override the back press behavior within the fragment
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showConfirmationDialog(childFragmentManager)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        // Make the createQuestionButton visible and re-establish the click listener
        createQuestionButton.visibility = View.VISIBLE
        createQuestionButton.setOnClickListener {
            showSelectQuestionTypeBottomSheet(childFragmentManager)
        }
    }

    private fun showConfirmationDialog(fragmentManager: FragmentManager) {
        val dialog = EmptyQuizDialogFragment()
        dialog.show(fragmentManager, EmptyQuizDialogFragment.TAG)
    }

    private fun showSelectQuestionTypeBottomSheet(fragmentManager: FragmentManager) {
        val bottomSheet = SelectQuestionTypeBottomSheet()
        bottomSheet.show(fragmentManager, "SelectQuestionTypeBottomSheet")
    }

    companion object {
        @JvmStatic
        fun newInstance(quizName: String) =
            QuizCreation().apply {
                arguments = Bundle().apply {
                    putString("quizName", quizName)
                }
            }
    }
}


// Create SelectQuestionTypeBottomSheet.kt (if you haven't already)
class SelectQuestionTypeBottomSheet : BottomSheetDialogFragment() {

    private var createQuestionButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_selection_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createQuestionButton = parentFragment?.view?.findViewById(R.id.create_question_button)

        val hideButton = {
            createQuestionButton?.visibility = View.GONE
        }

        view.findViewById<View>(R.id.fill_in_blanks_layout).setOnClickListener {
            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FillBlanks())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            dismiss()
            hideButton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        createQuestionButton?.visibility = View.VISIBLE // Show the button when the bottom sheet is dismissed
    }
}