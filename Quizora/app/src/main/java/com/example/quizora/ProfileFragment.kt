package com.example.quizora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizora.fucntions.Global

class ProfileFragment : Fragment() {
    private lateinit var tvNickName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAccess: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnEditProfile: Button = view.findViewById(R.id.btnEditProfile)

        tvNickName = view.findViewById(R.id.tvNickName)
        tvEmail = view.findViewById(R.id.tvEmailAddress)
        tvAccess = view.findViewById(R.id.tvAccess)

//        if (Global.ACCESS!! > 1) {
//            tvAccess.text = "Student"
//        }
//        else {
//            tvAccess.text = "Admin"
//        }
//        // Set initial values
//        tvNickName.text = Global.USERNAME
//        tvEmail.text = Global.EMAIL

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
