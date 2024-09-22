package com.example.lern.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lern.Screen
import com.example.lern.viewmodels.MainViewModel
import com.example.lern.viewmodels.states.TabId

@Composable
fun MainScreen(nav: NavController, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val tabTitles = listOf("All", "Favorite")

    Scaffold {
        Box(modifier = modifier.fillMaxSize().padding(it)) {
            TabRow(
                selectedTabIndex = uiState.selectedTab.ordinal
            ) {
                tabTitles.forEachIndexed { index, title ->
                    val tabType = TabId.values()[index]
                    Tab(
                        selected = uiState.selectedTab == tabType ,
                        onClick = { viewModel.changeTab(tabType) }) {
                        Text(text = title)
                    }
                }
            }
            LazyColumn(modifier = modifier.fillMaxWidth().weight(1f)) {
                items(uiState.posts) {
                    Text(
                        text = it.title,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = Bold
                        )
                    )

                    Text(
                        text = it.body,
                        style = TextStyle(
                            fontSize = 15.sp,
                        )
                    )

                    Spacer(modifier.height(10.dp))
                }
            }

            Button(
                modifier = modifier.align(BottomEnd),
                onClick = { nav.navigate(Screen.PostScreen.route) },

                ) {
                Text(text = "Upload")
            }
        }
    }
}