//package com.example.quizora
//
//import android.os.Build
//import android.os.Bundle
//import android.text.InputType
//import android.view.View
//import android.widget.EditText
//import android.widget.ImageButton
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.quizora.databinding.FragmentUpdatePasswordBinding
//
//class UpdatePassword : AppCompatActivity() {
//
//    private lateinit var binding: FragmentUpdatePasswordBinding
//    private var isCurrentPasswordVisible = false
//    private var isNewPasswordVisible = false
//    private var isReenterPasswordVisible = false
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentUpdatePasswordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Setup password toggles
//        setupPasswordToggles()
//    }
//
//    /**
//     * Set up toggle buttons for password visibility
//     */
//    private fun setupPasswordToggles() {
//        binding.togglePassword.setOnClickListener {
//            isCurrentPasswordVisible = !isCurrentPasswordVisible
//            togglePasswordVisibility(binding.cPassword, isCurrentPasswordVisible, binding.togglePassword)
//        }
//
//        binding.toggleNewPassword.setOnClickListener {
//            isNewPasswordVisible = !isNewPasswordVisible
//            togglePasswordVisibility(binding.nPassword, isNewPasswordVisible, binding.toggleNewPassword)
//        }
//
//        binding.toggleReenterPassword.setOnClickListener {
//            isReenterPasswordVisible = !isReenterPasswordVisible
//            togglePasswordVisibility(binding.rPassword, isReenterPasswordVisible, binding.toggleReenterPassword)
//        }
//    }
//
//    /**
//     * Toggles password visibility like LoginForm
//     */
//    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean, toggleButton: ImageButton) {
//        if (isVisible) {
//            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            toggleButton.setImageResource(R.drawable.baseline_remove_red_eye_24) // Show "eye open"
//        } else {
//            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            toggleButton.setImageResource(R.drawable.baseline_remove_red_eye_24) // Show "eye closed"
//        }
//        editText.setSelection(editText.text.length) // Keep cursor at the end
//    }
//
//    private fun confirmPasswordUpdate() {
//        AlertDialog.Builder(this)
//            .setTitle("Confirm Password Update")
//            .setMessage("Are you sure you want to update your password?")
//            .setPositiveButton("Yes") { _, _ -> savePassword() }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//
//    private fun savePassword() {
//        val currentPassword = binding.cPassword.text.toString().trim()
//        val newPassword = binding.nPassword.text.toString().trim()
//        val reenteredPassword = binding.rPassword.text.toString().trim()
//
//        when {
//            currentPassword.isEmpty() || newPassword.isEmpty() || reenteredPassword.isEmpty() -> {
//                showToast("⚠ Please fill in all fields!")
//            }
//            newPassword.length < 6 -> {
//                showToast("⚠ Password must be at least 6 characters!")
//            }
//            !newPassword.matches(Regex(".*[0-9].*")) -> {
//                showToast("⚠ Password must contain at least one number!")
//            }
//            !newPassword.matches(Regex(".*[A-Za-z].*")) -> {
//                showToast("⚠ Password must contain at least one letter!")
//            }
//            newPassword != reenteredPassword -> {
//                showToast("⚠ New passwords do not match!")
//            }
//            else -> {
//                // ✅ Perform actual password update (e.g., Firebase, API, local storage)
//                showToast("✅ Password updated successfully!")
//
//                // Close the activity after updating
//                finish()
//            }
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}
