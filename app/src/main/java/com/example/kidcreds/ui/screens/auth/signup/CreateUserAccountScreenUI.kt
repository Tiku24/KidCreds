package com.example.kidcreds.ui.screens.auth.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kidcreds.R
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.ui.components.AppTextField
import com.example.kidcreds.ui.navigation.CreateAccount
import com.example.kidcreds.ui.navigation.LogInAccount
import com.example.kidcreds.ui.navigation.Routes
import com.example.kidcreds.ui.screens.auth.login.TextGray
import com.example.kidcreds.ui.theme.KidCredsTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateUserAccountScreenUI(modifier: Modifier = Modifier, vm: SignUpViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        vm.event.collectLatest {
            when(it){
                is SignUpEvent.OnSignUpSuccess ->{
                    navController.navigate(LogInAccount)
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is SignUpEvent.ShowError ->{
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SignUpContent(
        name = name,
        email = email,
        password = password,
        onLogInClick = { navController.navigate(LogInAccount)},
        onSignUpClick = { vm.updateCreateUserAccount(UserEntity(name=name,email = email, password = password)) },
        onNameChange = { name = it },
        onEmailChange = { email = it },
        onPasswordChange = { password = it }
    )
}

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    password: String,
    onLogInClick : () -> Unit,
    onNameChange:(String) -> Unit,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onSignUpClick : () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Header: Logo and App Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.logo),contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "KidCreds",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Sign up for Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Enter your details to create your account",
            fontSize = 16.sp,
            color = TextGray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // White Card Container
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)) {

                Text("Full Name", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onBackground, unfocusedTextColor = MaterialTheme.colorScheme.onBackground, focusedBorderColor = MaterialTheme.colorScheme.surfaceTint),
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = { Text("Rahul") } // Match logical placeholder
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Email Field
                Text("Email", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onBackground, unfocusedTextColor = MaterialTheme.colorScheme.onBackground, focusedBorderColor = MaterialTheme.colorScheme.surfaceTint),
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("your@example.com") } // Match logical placeholder
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Field
                Text("Password", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onBackground, unfocusedTextColor = MaterialTheme.colorScheme.onBackground, focusedBorderColor = MaterialTheme.colorScheme.surfaceTint),
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text("••••••••") },
                    trailingIcon = {
                        Icon(Icons.Default.Visibility, contentDescription = null, tint = TextGray)
                    }
                )

                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 12.dp, end = 10.dp)
                        .clickable { /* Handle Forgot Password */ }
                )

                Spacer(modifier = Modifier.height(32.dp))
                // Sign Up Button
                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceTint),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Sign up", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Footer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already have an account? ", color = TextGray)
                    Text(
                        text = "Log In",
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onLogInClick() }
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES // This triggers Dark Mode
)
@Composable
fun SignUpUI() {
    KidCredsTheme {
        SignUpContent(
            name = "Rahul",
            email = "example@example.com",
            password = "123343",
            onLogInClick = {},
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {}
        )
    }
}

//    Column(modifier = modifier.fillMaxSize()) {
//        AppTextField(
//            value = name,
//            onValueChange = {
//                name = it
//            }
//        )
//        AppTextField(
//            value = email,
//            onValueChange = {
//                email = it
//            }
//        )
//        AppTextField(
//            value = password,
//            onValueChange = {
//                password = it
//            }
//        )
//        Button(onClick = {
//            vm.updateCreateUserAccount(UserEntity(
//                name = name,
//                email = email,
//                password = password
//            ))
//        }) {
//            Text("Create Account")
//        }
//
//        Button(onClick = {
//            navController.navigate(LogInAccount)
//        }) {
//            Text("Log In")
//        }
//    }
//}