package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class Profile2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_profile2, container, false)

        // Initialize buttons
        val btnBackProfile: Button = view.findViewById(R.id.btnBackProfile)
        val btnSaveProfile: Button = view.findViewById(R.id.btnSaveProfile)

        // Set button click listeners
        btnBackProfile.setOnClickListener { navigateBack() }
        btnSaveProfile.setOnClickListener { saveProfile() }

        return view
    }

    // Function to navigate back to ProfileFragment
    private fun navigateBack() {
        parentFragmentManager.popBackStack()
        restoreProfileUI()
    }

    // Function to handle saving and going back to ProfileFragment
    private fun saveProfile() {
        // Implement save logic if needed (e.g., updating database, shared preferences)

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
}
