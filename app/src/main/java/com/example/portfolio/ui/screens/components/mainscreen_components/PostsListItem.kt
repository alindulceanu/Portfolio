package com.example.portfolio.ui.screens.components.mainscreen_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.viewmodels.events.Events
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.FavoritePost
import com.example.portfolio.viewmodels.states.States
import com.example.portfolio.viewmodels.states.States.MainState
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostsListItem(post: PostsEntity, onFavorite: () -> Unit, onDelete: () -> Unit) {
    var collapsed by remember {
        mutableStateOf(true)
    }

    var dragX by remember {
        mutableFloatStateOf(0f)
    }

    val onClick: () -> Unit = { collapsed = !collapsed }

    val containerColor = if (post.isFavorited) {
        colorScheme.secondaryContainer
    } else {
        colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .combinedClickable (
                onClick = onClick,
                onLongClick = {},
                onDoubleClick = onFavorite
            )
            .offset {
                IntOffset(dragX.roundToInt(), 0)
            }
            .pointerInput(post.title) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if( dragX.absoluteValue > 500 )
                            onDelete()
                        dragX = 0f
                    }
                ) { _, dragAmount ->
                    dragX += dragAmount
                }
            },
        colors = cardColors(
            containerColor = containerColor
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Center
        ) {
            Text(
                text = post.title,
                style = typography.titleLarge
            )

            Text(
                text = post.body,
                style = typography.bodyLarge,
                maxLines = if(collapsed) 2 else Int.MAX_VALUE,
                color = colorScheme.onSurfaceVariant,
                overflow = Ellipsis,
            )
        }
    }
}