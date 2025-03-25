package com.example.quizora.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveUserEmail(email: String) {
        editor.putString("USER_EMAIL", email)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}
