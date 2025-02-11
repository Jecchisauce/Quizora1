package com.example.quizora

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Update Password Button
        val updateBtn = view.findViewById<Button>(R.id.updatebtn)
        updateBtn.setOnClickListener {
            showUpdatePasswordDialog()
        }

        // Delete Account Button
        val deleteBtn = view.findViewById<Button>(R.id.deletebtn)
        deleteBtn.setOnClickListener {
            showDeleteAccountConfirmationDialog()
        }

        // Logout Button
        val logoutBtn = view.findViewById<Button>(R.id.logoutbtn)
        logoutBtn.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return view
    }

    private fun showUpdatePasswordDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_update_password)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)

        val btnCancel = dialog.findViewById<Button>(R.id.btncancel)
        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteAccountConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_delete_account)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)

        val btnCancel = dialog.findViewById<Button>(R.id.btncancel1)
        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showLogoutConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_logout) // Ensure this layout exists
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)

        val btnCancel = dialog.findViewById<Button>(R.id.btncancel2)
        val btnConfirmLogout = dialog.findViewById<Button>(R.id.btnlogout)

        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirmLogout?.setOnClickListener {
            dialog.dismiss()
            logoutUser()
        }

        dialog.show()
    }

    private fun logoutUser() {
        val intent = Intent(requireContext(), LoginForm::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
