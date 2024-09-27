package com.example.portfolio.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.portfolio.ui.screens.components.mainscreen_components.PostsListItem
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents
import com.example.portfolio.viewmodels.states.States.MainState

@Composable
fun MainScreen(uiState: MainState, onEvent: (MainScreenEvents) -> Unit) {
    LazyColumn {
        items(uiState.posts) {post ->
            PostsListItem(post.title, post.body, post.isFavorited)
        }
    }
}

@Composable
@PreviewLightDark
fun PreviewMainScreen() {
TODO()
}