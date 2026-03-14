package com.example.kidcreds.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {

    fun updateCreateUserAccount(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.updateCreatUserAccount(userEntity)
        }
    }

    fun loginUserAccount(email: String, password: String){
        viewModelScope.launch {
            val data = userRepo.loginUserAccount(email = email, password = password)
            Log.d("User Data", "loginUserAccount: $data")
        }
    }
}