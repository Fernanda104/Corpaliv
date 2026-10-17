package com.example.corpalivapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.ui.navigation.AppNavigation

@Composable
fun App() {
    val context = LocalContext.current

    val database = remember(context) {
        CorpalivDatabase.obtenerInstancia(context)
    }

    AppNavigation(database = database)
}