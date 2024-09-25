package com.example.lern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lern.Screen.DeletedPostsScreen
import com.example.lern.Screen.MainScreen
import com.example.lern.Screen.PostScreen
import com.example.lern.Screen.PostScreen.route
import com.example.lern.ui.DeletedPostsScreen
import com.example.lern.ui.MainScreen
import com.example.lern.ui.PostScreen
import com.example.lern.ui.theme.LernTheme
import com.example.lern.viewmodels.DeletedPostsViewModel
import com.example.lern.viewmodels.MainViewModel
import com.example.lern.viewmodels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LernTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
    ) {
        composable(MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController, viewModel.uiState, viewModel::onEvent)
        }
        composable(PostScreen.route) {
            val viewModel = hiltViewModel<PostViewModel>()
            PostScreen(navController, viewModel::onEvent)
        }
        composable(DeletedPostsScreen.route) {
            val viewModel = hiltViewModel<DeletedPostsViewModel>()
            DeletedPostsScreen(navController, viewModel.uiState, viewModel::onEvent)
        }
    }
}

