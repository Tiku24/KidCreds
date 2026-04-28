package com.example.kidcreds.ui.screens.auth.login

import com.example.kidcreds.data.local.table.UserEntity

sealed class LogInUiState {
    data object Loading : LogInUiState()
    data class Data(val user: UserEntity) : LogInUiState()
    data object Empty : LogInUiState()
    data class Error(val message: String) : LogInUiState()
}

sealed class LogInEvent{
    data class OnLoginSuccess(val message: String) : LogInEvent()
    data class ShowError(val message: String) : LogInEvent()
}
