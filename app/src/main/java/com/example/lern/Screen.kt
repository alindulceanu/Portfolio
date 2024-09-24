package com.example.lern

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object PostScreen : Screen("post_screen")
    object DeletedPostsScreen : Screen("deleted_posts_screen")
}