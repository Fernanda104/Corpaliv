package com.example.corpalivapp

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.corpalivapp.ui.screens.HomeScreen
import com.example.corpalivapp.ui.theme.MiAppTheme

class MainActivity :
    ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        enableEdgeToEdge()
        setContent {
            MiAppTheme {
                App()
            }
        }
    }
}