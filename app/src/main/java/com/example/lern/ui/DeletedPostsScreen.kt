package com.example.lern.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lern.Screen.MainScreen
import com.example.lern.viewmodels.DeletedPostsViewModel


@Composable
fun DeletedPostsScreen (nav : NavController, viewModel: DeletedPostsViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Box(modifier = modifier.fillMaxSize().padding(it)) {
            LazyColumn(
                modifier = modifier.fillMaxWidth()
            ) {
                items(uiState.posts) { deletedItem ->
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically
                    ) {
                        Column (
                            modifier = modifier.weight(1f)
                        ){
                            Text(
                                text = deletedItem.title,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = Bold,
                                ),
                            )

                            Text(
                                text = deletedItem.body,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    textAlign = Justify
                                )
                            )

                            Spacer(modifier.height(10.dp))
                        }
                        Checkbox(
                            checked = deletedItem.isChecked,
                            onCheckedChange = { viewModel.checkPost(deletedItem) },
                            modifier = modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Row (
                modifier = modifier.align(BottomEnd),
            ) {
                Button(
                    onClick = { viewModel.restorePosts() },
                ) {
                    Text(text = "Restore")
                }
                Button(
                    onClick = { nav.navigate(MainScreen.route) },
                ) {
                    Text(text = "Back")
                }
            }
        }
    }
}