package com.example.kidcreds.ui.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {

//    private val _uiState = MutableStateFlow()

    fun updateCreateUserAccount(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.updateCreatUserAccount(userEntity)
        }
    }
}