package com.example.kidcreds.ui.navigation

import kotlinx.serialization.Serializable


interface Routes

@Serializable
object CreateAccount : Routes

@Serializable
object LogInAccount : Routes

@Serializable
object HomeScreen : Routes