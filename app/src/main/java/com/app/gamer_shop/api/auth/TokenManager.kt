package com.app.gamer_shop.api.auth

import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(private val prefs: SharedPreferences) {
    fun saveToken(token: String) {
        prefs.edit { putString("jwt", token) }
    }

    fun getToken(): String? {
        return prefs.getString("jwt", null)
    }

    fun clearToken() {
        prefs.edit { remove("jwt") }
    }
}