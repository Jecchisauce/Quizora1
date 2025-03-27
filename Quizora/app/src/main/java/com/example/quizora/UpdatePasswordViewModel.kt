package com.example.quizora

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdatePasswordViewModel : ViewModel() {
    val currentPassword = MutableLiveData("")
    val newPassword = MutableLiveData("")
    val reenteredPassword = MutableLiveData("")

    fun onSaveClicked() {
        // TODO: Implement save logic
    }

    fun onCancelClicked() {
        // TODO: Implement cancel logic
    }
}
