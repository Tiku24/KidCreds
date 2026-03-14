package com.example.kidcreds.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kidcreds.ui.screens.auth.CreateUserAccountScreenUI
import com.example.kidcreds.ui.screens.auth.LogInUserAccountScreenUI
import com.example.kidcreds.ui.viewmodel.UserViewModel

@Composable
fun NavApp(modifier: Modifier
) {

    val navController = rememberNavController()
    val userViewModel = hiltViewModel<UserViewModel>()
    NavHost(modifier = modifier.fillMaxSize(), navController = navController, startDestination = CreateAccount) {
        composable<HomeScreen> {}
        composable<CreateAccount> {
            CreateUserAccountScreenUI(vm = userViewModel, navController = navController)
        }
        composable<LogInAccount> {
            LogInUserAccountScreenUI(vm = userViewModel)
        }
    }
}