package com.example.portfolio.ui.screens.components.mainscreen_components

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp

@Composable
fun PostsListItem(title: String, body: String, isFavorite: Boolean) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp),
        colors = cardColors(
            containerColor = if (isFavorite)
                colorScheme.secondaryContainer
            else
                colorScheme.surfaceVariant
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Center
        ) {
            Text(
                text = title,
                style = typography.titleLarge
            )

            Text(
                text = body,
                style = typography.bodyLarge,
                maxLines = 2,
                color = colorScheme.onSurfaceVariant,
                overflow = Ellipsis,
            )
        }
    }
}