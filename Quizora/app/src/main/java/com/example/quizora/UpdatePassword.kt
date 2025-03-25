package com.example.quizora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizora.api.RetrofitClient
import com.example.quizora.models.ChangePasswordRequest
import com.example.quizora.models.ChangePasswordResponse
import com.example.quizora.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePassword : Fragment() {

    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etReenterPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_password, container, false)

        etCurrentPassword = view.findViewById(R.id.cPassword)
        etNewPassword = view.findViewById(R.id.nPassword)
        etReenterPassword = view.findViewById(R.id.rPassword)
        btnSave = view.findViewById(R.id.btnsave)
        btnCancel = view.findViewById(R.id.btncancel)

        sessionManager = SessionManager(requireContext()) // Initialize SessionManager

        btnSave.setOnClickListener {
            val email = sessionManager.getUserEmail()
            val currentPassword = etCurrentPassword.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val reenterPassword = etReenterPassword.text.toString().trim()

            if (email == null) {
                Toast.makeText(requireContext(), "Error: No email found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validateInputs(currentPassword, newPassword, reenterPassword)) {
                changePassword(email, currentPassword, newPassword)
            }
        }

        btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    private fun validateInputs(currentPassword: String, newPassword: String, reenterPassword: String): Boolean {
        return when {
            currentPassword.isEmpty() -> {
                etCurrentPassword.error = "Current password required"
                false
            }
            newPassword.isEmpty() -> {
                etNewPassword.error = "New password required"
                false
            }
            reenterPassword.isEmpty() -> {
                etReenterPassword.error = "Please re-enter new password"
                false
            }
            newPassword != reenterPassword -> {
                etReenterPassword.error = "Passwords do not match"
                false
            }
            else -> true
        }
    }

    private fun changePassword(email: String, currentPassword: String, newPassword: String) {
        val request = ChangePasswordRequest(email, currentPassword, newPassword)

        RetrofitClient.instance.changePassword(request)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), response.body()?.message ?: "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
