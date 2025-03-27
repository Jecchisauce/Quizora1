package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnEditProfile: Button = view.findViewById(R.id.btnEditProfile)

        // Ensure button is visible when fragment is loaded
        btnEditProfile.visibility = View.VISIBLE

        btnEditProfile.setOnClickListener {
            btnEditProfile.visibility = View.GONE // Hide button
            val bottomNav = activity?.findViewById<View>(R.id.bottom_nav1)
            bottomNav?.visibility = View.GONE // Hide bottom nav

            val profile2Fragment = Profile2()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,  // Smooth transition
                    android.R.anim.fade_out
                )
                .replace(R.id.fragment_container, profile2Fragment)
                .addToBackStack(null) // Enables back navigation
                .commit()
        }

        return view
    }

}
