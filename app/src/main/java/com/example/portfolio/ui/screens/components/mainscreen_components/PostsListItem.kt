package com.example.portfolio.ui.screens.components.mainscreen_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.ui.theme.PortfolioTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostsListItem(
    post: PostsEntity,
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null
) {
    val containerColor = if (post.isFavorited) {
        colorScheme.primary
    } else {
        colorScheme.secondaryContainer
    }

    var collapsed by remember {
        mutableStateOf(true)
    }

    val onClick: () -> Unit = { collapsed = !collapsed }

    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick,
            ),
        colors = cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Center
        ) {
            Text(
                text = post.title,
                style = typography.titleLarge,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = post.body,
                style = typography.bodyLarge,
                maxLines = if (collapsed) 2 else Int.MAX_VALUE,
                overflow = Ellipsis,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewPostsListItem() {
    PortfolioTheme {
        Surface(
            modifier = Modifier
                .background(colorScheme.background),
            tonalElevation = 3.dp
        ) {
            Column {
                PostsListItem(
                    post = PostsEntity(id = 0, title = "Bla Bla", body = "Bla Bla Bla")
                )

                PostsListItem(
                    post = PostsEntity(
                        id = 0,
                        title = "Bla Bla",
                        body = "Bla Bla Bla",
                        isFavorited = true
                    )
                )
            }
        }
    }
}