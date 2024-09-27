package com.example.portfolio

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screens(val route: String, val icon: ImageVector) {
    MAIN_SCREEN("main_screen", Default.DateRange),
    POST_SCREEN("post_screen", Default.Add),
    DELETED_POST_SCREEN("deleted_post_screen", Default.Delete)
}