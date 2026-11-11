package com.example.kidcreds.ui.screens.chore

sealed class ChoreUiState{
    data class OnChoreInserted(val message: String) : ChoreUiState()
    data class ShowError(val message: String) : ChoreUiState()
}