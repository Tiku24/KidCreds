package com.example.kidcreds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kidcreds.ui.navigation.NavApp
import com.example.kidcreds.ui.screens.auth.CreateUserAccountScreenUI
import com.example.kidcreds.ui.theme.KidCredsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KidCredsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}