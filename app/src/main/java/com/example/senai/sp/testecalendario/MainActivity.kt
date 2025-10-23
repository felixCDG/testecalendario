package com.example.senai.sp.testecalendario

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
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.senai.sp.testecalendario.screens.CalendarioScreen
import com.example.senai.sp.testecalendario.screens.HomeScreen
import com.example.senai.sp.testecalendario.ui.theme.TesteCalendarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {

            val navegacao = rememberNavController()

            NavHost(
                navController = navegacao,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(navController = navegacao)
                }
                composable("calendario") {
                    CalendarioScreen(navegacao = navegacao)
                }
            }

        }

        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesteCalendarioTheme {
        Greeting("Android")
    }
}

