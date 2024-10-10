package com.example.portfolio.ui

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screens(val route: String, val icon: ImageVector, val forNavbar: Boolean) {
    POST_SCREEN("post_screen", Default.Add, false),
    MAIN_SCREEN("main_screen", Default.Home, true),
    DELETED_POST_SCREEN("deleted_post_screen", Default.Delete, true)
}