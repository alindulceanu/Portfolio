package com.example.lern.ui

import android.util.Log.d
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lern.Screen
import com.example.lern.Screen.DeletedPostsScreen
import com.example.lern.Screen.PostScreen
import com.example.lern.viewmodels.MainViewModel
import com.example.lern.viewmodels.states.TabId
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(nav: NavController, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val tabTitles = listOf("All", "Favorite")

    Scaffold {
        Box(modifier = modifier.fillMaxSize().padding(it)) {
            LazyColumn(modifier = modifier.fillMaxWidth().offset(y = 20.dp)) {
                items(uiState.posts) {
                    var dragX by remember {
                        mutableFloatStateOf(0f)
                    }

                    var visible by remember {
                        mutableStateOf(true)
                    }

                    AnimatedVisibility(
                        visible = visible,
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = modifier.combinedClickable(
                                onClick = {},
                                onLongClick = {},
                                onDoubleClick = { viewModel.favoritePost(it) }
                            )
                                .offset {
                                    IntOffset(dragX.absoluteValue.roundToInt(), 0)
                                }
                                .pointerInput(it.id) {
                                    detectHorizontalDragGestures(
                                        onDragEnd = {
                                            d("MainScreen", "Drag: $dragX")
                                            if (dragX >= 600f) {
                                                visible = false
                                                viewModel.deletePost(it)
                                            } else
                                                dragX = 0f

                                        }
                                    ) { _, dragAmount ->
                                        dragX += dragAmount
                                    }
                                }
                        ) {
                            Text(
                                text = it.title,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = Bold,
                                ),
                            )

                            Text(
                                text = it.body,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    textAlign = Justify
                                )
                            )

                            Canvas(
                                modifier = modifier.padding(vertical = 10.dp)
                            ) {
                                drawLine (
                                    brush = Brush.horizontalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color(0x1f29ed),
                                            1f to Color(0x171eb0)
                                            )
                                    ),
                                    start = Offset(0f,0f),
                                    end = Offset(400.dp.toPx(), 0f),
                                    strokeWidth = 5.dp.toPx()
                                )
                            }
                        }
                    }
                }
            }
            Row (
                modifier = modifier.align(BottomEnd),
            ) {
                Button(
                    onClick = { nav.navigate(PostScreen.route) },
                    ) {
                    Text(text = "Upload")
                }
                Button(
                    onClick = { nav.navigate(DeletedPostsScreen.route) },
                ) {
                    Text(text = "Restore")
                }
            }
            TabRow(
                selectedTabIndex = uiState.selectedTab.ordinal,
            ) {
                d("MainScreen", "Selected tab: ${uiState.selectedTab}")
                tabTitles.forEachIndexed { index, title ->
                    val tabType = TabId.values()[index]
                    Tab(
                        selected = uiState.selectedTab == tabType,
                        onClick = {
                            d("MainScreen", "Clicked tab: $this")
                            viewModel.changeTab(tabType) }) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}