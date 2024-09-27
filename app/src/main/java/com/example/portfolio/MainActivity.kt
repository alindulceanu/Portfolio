package com.example.portfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.portfolio.Screens.DELETED_POST_SCREEN
import com.example.portfolio.Screens.MAIN_SCREEN
import com.example.portfolio.Screens.POST_SCREEN
import com.example.portfolio.ui.screens.DeletedPostsScreen
import com.example.portfolio.ui.screens.MainScreen
import com.example.portfolio.ui.screens.PostScreen
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.viewmodels.DeletedPostsViewModel
import com.example.portfolio.viewmodels.MainViewModel
import com.example.portfolio.viewmodels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.background),
                    tonalElevation = 3.dp
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
            MyNavigationBar(navController)
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
fun MyNavigationBar(nav: NavController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorScheme.secondaryContainer,
        contentColor = colorScheme.onSecondaryContainer,
    ) {
        Screens.entries
            .filter { it.forNavbar }
            .forEach { screen ->
            NavigationBarItem(
                selected = nav.currentBackStackEntry?.destination?.route == screen.route,
                onClick = { nav.navigate(screen.route) },
                icon = { Icon(screen.icon, contentDescription = null) },
            )
        }
    }
}
