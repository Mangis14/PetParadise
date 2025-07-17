package com.petparadise.game

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.petparadise.game.data.DriverFactory
import com.petparadise.game.di.initKoin
import com.petparadise.game.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

expect fun getPlatformDriverFactory(context: Any? = null): DriverFactory

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        val context = LocalContext.current
        initKoin(driverFactory = getPlatformDriverFactory(context))
    }) {
        MaterialTheme {
            HomeScreen()
        }
    }
}