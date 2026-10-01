package com.example.archmigrationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.archmigrationexample.ui.navigation.AppNavHost
import com.example.archmigrationexample.ui.theme.ArchMigrationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchMigrationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}
