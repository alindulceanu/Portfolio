package com.example.portfolio.ui.post_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.ui.post_screen.PostViewModel.PostScreenEvents
import com.example.portfolio.ui.post_screen.PostViewModel.PostScreenEvents.PostEntity

@Composable
fun PostScreen(onEvent: (PostScreenEvents) -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var body by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .align(Center)
                .fillMaxWidth()
                .size(250.dp),
            shape = shapes.large,
            colors = cardColors(
                colorScheme.primaryContainer
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    shape = shapes.extraLarge,
                    textStyle = typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = body,
                    onValueChange = { body = it },
                    shape = shapes.extraLarge,
                    textStyle = typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {onEvent(PostEntity(title, body))}
                ) {
                    Text(
                        text = "Post",
                        style = typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewPostScreen() {
    PortfolioTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background) ,
            tonalElevation = 1.dp
        ) {
            PostScreen({})
        }
    }
}