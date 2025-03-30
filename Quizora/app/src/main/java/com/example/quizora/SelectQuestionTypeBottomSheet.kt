import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.quizora.FillBlanks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.quizora.R // Replace with your package name

class SelectQuestionTypeBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_selection_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.fill_in_blanks_layout).setOnClickListener {
            // Handle Fill in the blanks selection
            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FillBlanks()) // Replace R.id.fragment_container with your container ID
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack
            fragmentTransaction.commit()
            dismiss() // Close the bottom sheet
        }
        view.findViewById<LinearLayout>(R.id.multiple_choice_layout).setOnClickListener {
            // Handle Multiple Choice selection
            // ...
            dismiss() // Close the bottom sheet
        }

        view.findViewById<LinearLayout>(R.id.true_false_layout).setOnClickListener {
            // Handle True or False selection
            // ...
            dismiss() // Close the bottom sheet
        }
    }

    companion object {
        const val TAG = "SelectQuestionTypeBottomSheet"

        fun show(fragmentManager: FragmentManager) {
            SelectQuestionTypeBottomSheet().show(fragmentManager, TAG)
        }
    }
}