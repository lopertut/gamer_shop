package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.ApiService
import com.app.gamer_shop.api.LoginRequest
import com.app.gamer_shop.api.RegistrationRequest
import com.app.gamer_shop.api.auth.TokenManager
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val api: ApiService, private val tokenManager: TokenManager) {
    suspend fun login(email: String, password: String): Boolean {
        return try {
            val response = api.login(LoginRequest(email, password))

            tokenManager.saveToken(response.token)

            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun registration(name: String, email: String, password: String): Boolean {
        val response = api.registration(RegistrationRequest(name, email, password))

        return response.status
    }
}