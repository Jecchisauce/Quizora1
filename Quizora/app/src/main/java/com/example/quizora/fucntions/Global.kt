package com.example.quizora.fucntions

import android.content.Context
import android.content.SharedPreferences

object Global {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("GlobalPrefs", Context.MODE_PRIVATE)
        }
    }

    var ID: Int?
        get() = sharedPreferences?.getInt("ID", -1)?.takeIf { it != -1 }
        set(value) {
            sharedPreferences?.edit()?.putInt("ID", value ?: -1)?.apply()
        }

    var USERNAME: String?
        get() = sharedPreferences?.getString("USERNAME", "Default")
        set(value) {
            sharedPreferences?.edit()?.putString("USERNAME", value)?.apply()
        }

    var EMAIL: String?
        get() = sharedPreferences?.getString("EMAIL", "hello@theotherside.com")
        set(value) {
            sharedPreferences?.edit()?.putString("EMAIL", value)?.apply()
        }

    var TOTAL_QUIZZES: Int
        get() = sharedPreferences?.getInt("TOTAL_QUIZZES", 0) ?: 0
        set(value) {
            sharedPreferences?.edit()?.putInt("TOTAL_QUIZZES", value)?.apply()
        }

    var ACCESS: Int?
        get() = sharedPreferences?.getInt("ACCESS", -1)?.takeIf { it != -1 }
        set(value) {
            sharedPreferences?.edit()?.putInt("ACCESS", value ?: -1)?.apply()
        }

    var LOGGED: Boolean
        get() = sharedPreferences?.getBoolean("LOGGED", false) ?: false
        set(value) {
            sharedPreferences?.edit()?.putBoolean("LOGGED", value)?.apply()
        }

    var BASE_URL: String?
        get() = sharedPreferences?.getString("BASE_URL", "192.168.21.132")
        set(value) {
            sharedPreferences?.edit()?.putString("BASE_URL", value)?.apply()
        }
}