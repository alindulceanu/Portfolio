package com.example.lern.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lern.Screen
import com.example.lern.viewmodels.PostViewModel
import com.example.lern.viewmodels.events.Events
import com.example.lern.viewmodels.events.Events.PostScreenEvents
import com.example.lern.viewmodels.events.Events.PostScreenEvents.PostEntity
import com.example.lern.viewmodels.states.States
import com.example.lern.viewmodels.states.States.EmptyState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PostScreen(nav: NavController, onEvent: (PostScreenEvents) -> Unit) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var title by remember {
                mutableStateOf("")
            }
            var body by remember {
                mutableStateOf("")
            }
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Enter Title") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = body,
                onValueChange = { body = it },
                label = { Text(text = "Enter Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Row {
                Button(
                    onClick = { onEvent(PostEntity(title, body)) }
                ) {
                    Text("Upload")
                }
                Button(
                    onClick = { nav?.navigate(Screen.MainScreen.route) }
                ) {
                    Text("Back")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPostScreen() {
    PostScreen(rememberNavController()) {}
}