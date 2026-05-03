package com.example.kidcreds.ui.screens.auth.signup

import com.example.kidcreds.data.local.table.UserEntity

sealed class SignUpUiState {
    data object Loading : SignUpUiState()
    data class Data(val user: UserEntity) : SignUpUiState()
    data object Empty : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}

sealed class SignUpEvent{
    data class OnSignUpSuccess(val message: String) : SignUpEvent()
    data class ShowError(val message: String) : SignUpEvent()
}
