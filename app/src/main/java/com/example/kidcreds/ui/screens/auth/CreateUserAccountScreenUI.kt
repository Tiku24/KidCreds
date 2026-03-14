package com.example.kidcreds.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.ui.components.AppTextField
import com.example.kidcreds.ui.navigation.LogInAccount
import com.example.kidcreds.ui.viewmodel.UserViewModel

@Composable
fun CreateUserAccountScreenUI(modifier: Modifier = Modifier,vm: UserViewModel,navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        AppTextField(
            value = name,
            onValueChange = {
                name = it
            }
        )
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
            vm.updateCreateUserAccount(UserEntity(
                name = name,
                email = email,
                password = password
            ))
        }) {
            Text("Create Account")
        }

        Button(onClick = {
            navController.navigate(LogInAccount)
        }) {
            Text("Log In")
        }
    }
}