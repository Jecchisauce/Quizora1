package com.example.quizora

import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class Profile2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_profile2, container, false)

        // Initialize UI elements
        val btnBackProfile: Button = view.findViewById(R.id.btnBackProfile)
        val btnSaveProfile: Button = view.findViewById(R.id.btnSaveProfile)
        val editTextName: EditText = view.findViewById(R.id.Usernamep)
        val editTextEmail: EditText = view.findViewById(R.id.et_email)
        val editTextRole: EditText = view.findViewById(R.id.editTextRolep)

        // Apply emoji restriction filter
        val emojiFilter = EmojiInputFilter()
        editTextName.filters = arrayOf(emojiFilter)
        editTextEmail.filters = arrayOf(emojiFilter)
        editTextRole.filters = arrayOf(emojiFilter)

        // Set button click listeners
        btnBackProfile.setOnClickListener { navigateBack() }
        btnSaveProfile.setOnClickListener { saveProfile() }

        return view
    }

    override fun onResume() {
        super.onResume()
        disableSystemBackButton()
    }

    // Function to disable Android system back button
    private fun disableSystemBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Do nothing, effectively disabling back button
                }
            }
        )
    }

    // Function to navigate back to ProfileFragment using UI button
    private fun navigateBack() {
        parentFragmentManager.popBackStack()
        restoreProfileUI()
    }

    // Function to handle saving and going back to ProfileFragment
    private fun saveProfile() {
        parentFragmentManager.popBackStack() // Navigate back
        restoreProfileUI()
    }

    // Restore visibility of UI elements when returning to ProfileFragment
    private fun restoreProfileUI() {
        activity?.findViewById<View>(R.id.bottom_nav1)?.visibility = View.VISIBLE
        activity?.findViewById<View>(R.id.btnEditProfile)?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        restoreProfileUI()
    }

    // Custom InputFilter to block emojis
    class EmojiInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?, start: Int, end: Int,
            dest: Spanned?, dstart: Int, dend: Int
        ): CharSequence? {
            source?.let {
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                        return "" // Block emojis
                    }
                }
            }
            return null // Accept normal input
        }
    }
}
