package com.example.quizora

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.quizora.fucntions.Global
import com.example.quizora.fucntions.RetrofitClient
import kotlinx.coroutines.launch

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

        val viewModel = UpdatePasswordViewModel()

        val btnCancel = dialog.findViewById<Button>(R.id.btncancel)
        val btnSave = dialog.findViewById<Button>(R.id.btnsave)
        val currentPasswordField = dialog.findViewById<EditText>(R.id.cPassword)
        val newPasswordField = dialog.findViewById<EditText>(R.id.nPassword)
        val reenteredPasswordField = dialog.findViewById<EditText>(R.id.rPassword)

        val toggleCurrentPassword = dialog.findViewById<ImageButton>(R.id.togglePassword)
        val toggleNewPassword = dialog.findViewById<ImageButton>(R.id.toggleNewPassword)
        val toggleReenterPassword = dialog.findViewById<ImageButton>(R.id.toggleReenterPassword)

        var isCurrentPasswordVisible = false
        var isNewPasswordVisible = false
        var isReenterPasswordVisible = false

        // Regex to prevent emojis
        val emojiRegex = Regex("[^\\p{L}\\p{N}\\p{P}\\p{Z}]")

        // Toggle password visibility
        toggleCurrentPassword.setOnClickListener {
            isCurrentPasswordVisible = !isCurrentPasswordVisible
            currentPasswordField.inputType = if (isCurrentPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            currentPasswordField.setSelection(currentPasswordField.text.length)
        }

        toggleNewPassword.setOnClickListener {
            isNewPasswordVisible = !isNewPasswordVisible
            newPasswordField.inputType = if (isNewPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            newPasswordField.setSelection(newPasswordField.text.length)
        }

        toggleReenterPassword.setOnClickListener {
            isReenterPasswordVisible = !isReenterPasswordVisible
            reenteredPasswordField.inputType = if (isReenterPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            reenteredPasswordField.setSelection(reenteredPasswordField.text.length)
        }

        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        btnSave?.setOnClickListener {
            val currentPassword = currentPasswordField.text.toString().trim()
            val newPassword = newPasswordField.text.toString().trim()
            val reenteredPassword = reenteredPasswordField.text.toString().trim()

            when {
                currentPassword.isEmpty() || newPassword.isEmpty() || reenteredPassword.isEmpty() -> {
                    Toast.makeText(requireContext(), "⚠ Please fill in all fields!", Toast.LENGTH_SHORT).show()
                }
                newPassword.length < 8 || newPassword.length > 12 -> {
                    Toast.makeText(requireContext(), "⚠ Password must be 8-12 characters long!", Toast.LENGTH_SHORT).show()
                }
                newPassword.contains(emojiRegex) -> {
                    Toast.makeText(requireContext(), "⚠ Password must not contain emojis or special characters!", Toast.LENGTH_SHORT).show()
                }
                !newPassword.matches(Regex(".*[0-9].*")) -> {
                    Toast.makeText(requireContext(), "⚠ Password must contain at least one number!", Toast.LENGTH_SHORT).show()
                }
                !newPassword.matches(Regex(".*[A-Za-z].*")) -> {
                    Toast.makeText(requireContext(), "⚠ Password must contain at least one letter!", Toast.LENGTH_SHORT).show()
                }
                newPassword != reenteredPassword -> {
                    Toast.makeText(requireContext(), "⚠ New passwords do not match!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.onSaveClicked()
                    Toast.makeText(requireContext(), "✅ Password updated successfully!", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }
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
        val btnDelete = dialog.findViewById<Button>(R.id.btnsave)
        val passwordField = dialog.findViewById<EditText>(R.id.cPassword)
        val togglePassword = dialog.findViewById<ImageButton>(R.id.deletetogglePassword)

        var isPasswordVisible = false

        // Toggle password visibility
        togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            passwordField.inputType = if (isPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordField.setSelection(passwordField.text.length)
        }

        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        btnDelete?.setOnClickListener {
            val enteredPassword = passwordField?.text.toString().trim()

            when {
                enteredPassword.isEmpty() -> {
                    Toast.makeText(requireContext(), "⚠ Please enter your password!", Toast.LENGTH_SHORT).show()
                }
                enteredPassword.length < 6 -> {
                    Toast.makeText(requireContext(), "⚠ Password must be at least 6 characters!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    deleteAccount(enteredPassword)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }


    private fun deleteAccount(password: String) {
        lifecycleScope.launch {
            var userId = 1
            if (Global.ID != null) {userId = Global.ID!!}
            try {
                val response = RetrofitClient.api.delUser(userId) // Replace `apiService` with your Retrofit instance
                if (response.success) {
                    Log.d("DeleteUser", "User deleted successfully")
                } else {
                    Log.e("DeleteUser", "Failed: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("DeleteUser", "Error: ${e.message}")
            }
        }
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
