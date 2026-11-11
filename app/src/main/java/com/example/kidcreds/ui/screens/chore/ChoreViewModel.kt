package com.example.kidcreds.ui.screens.chore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidcreds.data.local.table.ChoreEntity
import com.example.kidcreds.data.repository.ChoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoreViewModel @Inject constructor(private val repo: ChoreRepo): ViewModel(){

    private val _uiState = MutableStateFlow<List<ChoreEntity>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ChoreUiState>()
    val event = _event.asSharedFlow()

    init {
        loadChores()
    }

    private fun loadChores(){
        viewModelScope.launch {
            // Check once if empty to seed data
            val initialChores = repo.getChores().first()
            if (initialChores.isEmpty()){
                repo.choreDummyData()
            }
            
            // Then observe the flow for updates
            repo.getChores().collect { chore ->
                _uiState.value = chore
                Log.d("CHORE", "loadChores Emission: ${chore.size} items")
            }
        }
    }

    fun addChore(chore: ChoreEntity) {
        viewModelScope.launch {
            runCatching {
                repo.insertChore(chore)
            }.onSuccess {
                _event.emit(ChoreUiState.OnChoreInserted("Chore Added"))
            }.onFailure {
                _event.emit(ChoreUiState.ShowError("Failed To Add Chore"))
            }
        }
    }
}

