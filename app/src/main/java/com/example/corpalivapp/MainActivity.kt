package com.example.corpalivapp

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.corpalivapp.ui.screens.HomeScreen
import com.example.corpalivapp.ui.theme.MiAppTheme
import com.example.corpalivapp.notificaciones.NotificacionColaboracion

class MainActivity :
    ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        enableEdgeToEdge()
        NotificacionColaboracion.crearCanal(this)   // registra el canal al arrancar la app
        setContent {
            MiAppTheme {
                App()
            }
        }
    }
}