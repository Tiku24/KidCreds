package com.example.kidcreds.ui.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidcreds.data.datastore.KidCredsPrefDatastore
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import com.example.kidcreds.ui.screens.auth.login.LogInEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event = _event.asSharedFlow()

    fun updateCreateUserAccount(userEntity: UserEntity){
        viewModelScope.launch {
            if (userEntity.email.isBlank() || userEntity.password.isBlank() || userEntity.name.isBlank()){
                _event.emit(SignUpEvent.ShowError("Please fill in all fields"))
            }else{
                userRepo.updateCreatUserAccount(userEntity)
                _event.emit(SignUpEvent.OnSignUpSuccess("Account created successfully"))
            }
        }
    }
}