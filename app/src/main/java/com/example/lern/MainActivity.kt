package com.example.lern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lern.Screens.DELETED_POST_SCREEN
import com.example.lern.Screens.MAIN_SCREEN
import com.example.lern.Screens.POST_SCREEN
import com.example.lern.ui.screens.DeletedPostsScreen
import com.example.lern.ui.screens.MainScreen
import com.example.lern.ui.screens.PostScreen
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
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
                {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = MAIN_SCREEN.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(MAIN_SCREEN.route) {
                val viewModel = hiltViewModel<MainViewModel>()
                val uiState by viewModel.uiState.collectAsState()
                MainScreen(uiState, viewModel::onEvent)
            }
            composable(POST_SCREEN.route) {
                val viewModel = hiltViewModel<PostViewModel>()
                PostScreen(navController, viewModel::onEvent)
            }
            composable(DELETED_POST_SCREEN.route) {
                val viewModel = hiltViewModel<DeletedPostsViewModel>()
                val uiState by viewModel.uiState.collectAsState()
                DeletedPostsScreen(navController, uiState, viewModel::onEvent)
            }
        }
    }
}

@Composable
fun NavigationBar(nav: NavController) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        Screens.entries.forEach { screen ->
            NavigationBarItem(
                selected = nav.currentBackStackEntry?.destination?.route == screen.route,
                onClick = { nav.navigate(screen.route) },
                icon = { Icon(screen.icon, contentDescription = null) },
                label = {""}
            )
        }
    }
}
