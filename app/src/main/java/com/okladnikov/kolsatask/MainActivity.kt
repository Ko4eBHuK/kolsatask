package com.okladnikov.kolsatask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.okladnikov.kolsatask.navigation.SetupNavGraph
import com.okladnikov.kolsatask.ui.theme.KolsaTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            KolsaTaskTheme {
                SetupNavGraph(
                    navController = navController,
                    activity = this
                )
            }
        }
    }
}
