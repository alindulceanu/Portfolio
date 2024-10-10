package com.example.portfolio.ui.screens

import android.util.Log.d
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.FabPosition.Companion.End
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults.CircularStrokeWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.ui.screens.components.OnLoading
import com.example.portfolio.ui.screens.components.PostsListItem
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.viewmodels.MainViewModel
import com.example.portfolio.viewmodels.MainViewModel.Events.ChangeTab
import com.example.portfolio.viewmodels.MainViewModel.Events.DeletePost
import com.example.portfolio.viewmodels.MainViewModel.Events.FavoritePost
import com.example.portfolio.viewmodels.MainViewModel.MainScreenTabId
import com.example.portfolio.viewmodels.MainViewModel.MainScreenTabId.TAB_TWO
import com.example.portfolio.viewmodels.MainViewModel.States.Loading
import com.example.portfolio.viewmodels.MainViewModel.States.MainState
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun MainScreen (state: MainViewModel.States, onEvent: (MainViewModel.Events) -> Unit) {
    when (state) {
        Loading -> OnLoading()
        is MainState -> MainScreenList(state) { onEvent(it) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenList(state: MainState, onEvent: (MainViewModel.Events) -> Unit) {
    d("Main_Screen", "Recomposition")
    Scaffold(
        topBar = { TopTabs(state) { onEvent(ChangeTab(it)) } },
        floatingActionButton = {},
        floatingActionButtonPosition = End
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(
                items = state.posts,
                key = { post -> post.id },
            ) { post ->
                var visible by remember {
                    mutableStateOf(true)
                }
                LaunchedEffect(visible) {
                    if (!visible) {
                        delay(300)
                        onEvent(DeletePost(post))
                    }
                }

                val onDelete: () -> Unit = {
                    visible = false
                }

                var dragX by remember {
                    mutableFloatStateOf(0f)
                }



                AnimatedVisibility(
                    visible = visible,
                    exit = fadeOut()
                ) {
                    PostsListItem(
                        post = post,
                        onDoubleClick = { onEvent(FavoritePost(post)) },
                        modifier = Modifier
                            .offset {
                                IntOffset(dragX.roundToInt(), 0)
                            }
                            .pointerInput(post.title) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (dragX.absoluteValue > 500)
                                            onDelete()
                                        dragX = 0f
                                    }
                                ) { _, dragAmount ->
                                    dragX += dragAmount
                                }
                            },
                    )
                }
            }
        }
    }
}

@Composable
fun TopTabs(state: MainState, onClick: (MainScreenTabId) -> Unit) {
    TabRow(
        selectedTabIndex = state.selectedTab.ordinal,
        containerColor = colorScheme.primaryContainer,
        contentColor = colorScheme.onPrimaryContainer,
    ) {
        MainScreenTabId.entries.forEach { tab ->
            Tab(
                selected = tab == state.selectedTab,
                onClick = { onClick(tab) },
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    Icon(tab.icon, contentDescription = null)
                    Text(
                        text = tab.text,
                        style = typography.labelSmall,
                        color = colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}


@Composable
@PreviewLightDark
fun PreviewMainScreen() {
    PortfolioTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            tonalElevation = 1.dp
        ) {
            MainScreen(
                MainState(
                    posts = (0..10).map { id ->
                        if (id % 2 == 0)
                            PostsEntity(id, "Bla Bla", "BlaBlaBla")
                        else
                            PostsEntity(id, "Bla Bla", "BlaBlaBla", isFavorited = true)
                    },
                    selectedTab = TAB_TWO
                )
            ) { }
        }
    }
}