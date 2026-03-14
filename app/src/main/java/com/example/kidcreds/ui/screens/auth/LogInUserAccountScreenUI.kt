package com.example.kidcreds.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.kidcreds.ui.components.AppTextField
import com.example.kidcreds.ui.viewmodel.UserViewModel

@Composable
fun LogInUserAccountScreenUI(modifier: Modifier = Modifier, vm: UserViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column() {
        AppTextField(
            value = email,
            onValueChange = {
                email = it
            }
        )
        AppTextField(
            value = password,
            onValueChange = {
                password = it
            }
        )
        Button(onClick = {
            vm.loginUserAccount(email = email, password = password)
        }) {
            Text("Log In")
        }
    }
}