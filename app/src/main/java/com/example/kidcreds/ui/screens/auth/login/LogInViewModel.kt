package com.example.kidcreds.ui.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidcreds.data.datastore.KidCredsPrefDatastore
import com.example.kidcreds.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val kidCredsPrefDatastore: KidCredsPrefDatastore
): ViewModel() {

    private val _uiState = MutableStateFlow<LogInUiState>(LogInUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LogInEvent>()
    val event = _event.asSharedFlow()


    fun loginUserAccount(email: String, password: String){
        viewModelScope.launch {
            runCatching {
                userRepo.loginUserAccount(email = email, password = password)
            }.onSuccess { user ->
                Log.d("Success", "loginUserAccount: $user")
                if(user != null){
                    _event.emit(LogInEvent.OnLoginSuccess(message = "login success"))
                    kidCredsPrefDatastore.saveLoginStatus(true)
                }else{
                    _event.emit(LogInEvent.ShowError(message = "login failed"))
                }
            }.onFailure {
                Log.d("Error", "loginUserAccount: $it")
                _event.emit(LogInEvent.ShowError(message = "login failed"))
            }
        }
    }
}