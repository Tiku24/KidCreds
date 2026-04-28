package com.example.kidcreds.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kidcreds.data.datastore.KidCredsPrefDatastore
import com.example.kidcreds.ui.screens.auth.signup.CreateUserAccountScreenUI
import com.example.kidcreds.ui.screens.auth.login.LogInUserAccountScreenUI
import com.example.kidcreds.ui.screens.auth.login.LogInViewModel
import com.example.kidcreds.ui.screens.auth.signup.SignUpViewModel

@Composable
fun NavApp(
    modifier: Modifier,
    session: KidCredsPrefDatastore
) {

    val navController = rememberNavController()
    val signUpViewModel = hiltViewModel<SignUpViewModel>()
    val logInViewModel = hiltViewModel<LogInViewModel>()
    val isLoggedIn by session.getLoginStatus().collectAsState(initial = null)
    if (isLoggedIn == null) {
        return
    }

    NavHost(modifier = modifier.fillMaxSize(), navController = navController, startDestination = if (isLoggedIn == true) HomeScreen else LogInAccount) {
        composable<HomeScreen> {
            Text("Home Screen")
        }
        composable<CreateAccount> {
            CreateUserAccountScreenUI(vm = signUpViewModel, navController = navController)
        }
        composable<LogInAccount> {
            LogInUserAccountScreenUI(vm = logInViewModel, navController = navController)
        }
    }
}