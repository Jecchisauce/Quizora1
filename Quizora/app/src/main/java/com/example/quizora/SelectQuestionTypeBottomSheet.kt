import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.quizora.FillBlanks
import com.example.quizora.MultipleChoiceFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.quizora.R // Replace with your package name
import com.example.quizora.TrueFalseFragment

class SelectQuestionTypeBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_selection_type, container, false)
    }



    companion object {
        const val TAG = "SelectQuestionTypeBottomSheet"

        fun show(fragmentManager: FragmentManager) {
            SelectQuestionTypeBottomSheet().show(fragmentManager, TAG)
        }
    }
}