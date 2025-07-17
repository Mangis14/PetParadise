package com.petparadise.game

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.petparadise.game.feature.screen1.GameScreen
import com.petparadise.game.feature.screen2.Screen2
import com.petparadise.game.feature.screen3.Screen3
import com.petparadise.game.navigation.Screen

@Composable
fun HomeScreen() {
    var current: Screen by remember { mutableStateOf(Screen.Screen1) }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                listOf(Screen.Screen1, Screen.Screen2, Screen.Screen3).forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = current == screen,
                        onClick = { current = screen }
                    )
                }
            }
        }
    ) { paddingValues ->
        when (current) {
            Screen.Screen1 -> GameScreen()
            Screen.Screen2 -> Screen2()
            Screen.Screen3 -> Screen3()
        }
    }
}

