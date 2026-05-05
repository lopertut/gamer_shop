package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableSharedFlow<Boolean>()
    val loginResult = _loginResult.asSharedFlow()

    private val _registerResult = MutableSharedFlow<Boolean>()
    val registerResult = _registerResult.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.login(email, password)
            _loginResult.emit(success)
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.registration(name, email, password)
            _registerResult.emit(success)
        }
    }
}
