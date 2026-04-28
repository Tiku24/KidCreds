package com.example.kidcreds.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import com.example.kidcreds.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kidcreds.ui.components.AppTextField
import com.example.kidcreds.ui.navigation.CreateAccount
import com.example.kidcreds.ui.navigation.HomeScreen
import kotlinx.coroutines.flow.collectLatest

// Define the custom colors from the image
val BackgroundPurple = Color(0xFFE5E7FD)
val ButtonBlue = Color(0xFF4A72E8)
val TextDark = Color(0xFF1A1C1E)
val TextGray = Color(0xFF74777F)
@Composable
fun LogInUserAccountScreenUI(
    modifier: Modifier = Modifier,
    vm: LogInViewModel,
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        vm.event.collectLatest {
            when(it){
                is LogInEvent.OnLoginSuccess -> {
                    navController.navigate(HomeScreen)
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is LogInEvent.ShowError -> {
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LogInContent(
        email = email,
        password = password,
        onLogInClick = { vm.loginUserAccount(email = email, password = password) },
        onSignUpClick = { navController.navigate(CreateAccount) },
        onEmailChange = { email = it },
        onPasswordChange = { password = it }
    )
}

@Composable
fun LogInContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onLogInClick : () -> Unit,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onSignUpClick : () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPurple)
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
                color = TextDark
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Sign in to your Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )
        Text(
            text = "Enter your email and password to login",
            fontSize = 16.sp,
            color = TextGray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // White Card Container
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Email Field
                Text("Email", fontWeight = FontWeight.SemiBold, color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("your@example.com") } // Match logical placeholder
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Field
                Text("Password", fontWeight = FontWeight.SemiBold, color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text("••••••••") },
                    trailingIcon = {
                        Icon(Icons.Default.Visibility, contentDescription = null, tint = TextGray)
                    }
                )

                Text(
                    text = "Forgot Password?",
                    color = ButtonBlue,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 12.dp, end = 10.dp)
                        .clickable { /* Handle Forgot Password */ }
                )

                Spacer(modifier = Modifier.height(32.dp))
                // Log In Button
                Button(
                    onClick = onLogInClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Footer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Don't have an account? ", color = TextGray)
                    Text(
                        text = "Sign up",
                        color = ButtonBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onSignUpClick() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogInUI() {
    LogInContent(
        email = "example@example.com",
        password = "123343",
        onLogInClick = {},
        onEmailChange = {},
        onPasswordChange = {}
    )
}