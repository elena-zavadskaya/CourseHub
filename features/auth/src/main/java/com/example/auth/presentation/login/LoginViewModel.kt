package com.example.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.interactor.AuthInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authInteractor: AuthInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            try {
                val success = authInteractor.login(email, password)
                _uiState.value = if (success) {
                    LoginUiState.Success
                } else {
                    LoginUiState.Error("Неверный email или пароль")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Ошибка подключения: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}