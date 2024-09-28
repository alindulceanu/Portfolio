package com.example.portfolio.viewmodels.states

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainScreenTabId(val text: String, val icon: ImageVector) {
    TAB_ONE("Home", Default.Home),
    TAB_TWO("Favorite", Default.Favorite)
}