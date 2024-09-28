package com.example.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.tools.toPostsEntity
import com.example.portfolio.ui.screens.components.mainscreen_components.PostsListItem
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.CheckPost
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.RestoreAllPosts
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.RestorePosts
import com.example.portfolio.viewmodels.states.DeletedPosts
import com.example.portfolio.viewmodels.states.States.DeletedPostsState


@Composable
fun DeletedPostsScreen(
    uiState: DeletedPostsState,
    onEvent: (DeletedPostsScreenEvents) -> Unit,
) {
    Scaffold(
        topBar = { TopButtons({ onEvent(RestorePosts) }, { onEvent(RestoreAllPosts) }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(uiState.posts) { deletedPost ->
                Box(

                ) {
                    PostsListItem(post = deletedPost.toPostsEntity())
                    Checkbox(
                        checked = deletedPost.isChecked,
                        onCheckedChange = { onEvent(CheckPost(deletedPost)) },
                        modifier = Modifier
                            .align(CenterEnd)
                            .padding(end = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopButtons(onRestoreClick: () -> Unit, onRestoreAllClick: () -> Unit) {
    data class Button(val text: String, val action: () -> Unit)

    val buttons = listOf(
        Button("Restore", onRestoreClick),
        Button("Restore All", onRestoreAllClick)
    )
    Surface(
        color = colorScheme.primaryContainer,
        contentColor = colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            buttons.forEach { button ->
                Button(
                    onClick = button.action,
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = colorScheme.primaryContainer,
                        contentColor = colorScheme.onPrimaryContainer,
                        disabledContentColor = colorScheme.primaryContainer,
                        disabledContainerColor = colorScheme.primaryContainer
                    )
                ) {
                    Text(text = button.text)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDeletedPostsScreen() {
    PortfolioTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            tonalElevation = 1.dp
        ) {
            DeletedPostsScreen(
                uiState = DeletedPostsState(
                    posts = listOf(
                        DeletedPosts(
                            0,
                            "Bla Bla",
                            "Bla Bla Bla"
                        )
                    )
                ), {})
        }
    }
}