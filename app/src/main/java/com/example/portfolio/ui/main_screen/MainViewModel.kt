package com.example.portfolio.ui.main_screen

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.ui.main_screen.MainViewModel.MainScreenTabId.TAB_ONE
import com.example.portfolio.ui.main_screen.MainViewModel.MainScreenTabId.TAB_TWO
import com.example.portfolio.ui.main_screen.MainViewModel.States.Loading
import com.example.portfolio.ui.main_screen.MainViewModel.States.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val currentTab: MutableStateFlow<MainScreenTabId> = MutableStateFlow(TAB_ONE)


    val state: StateFlow<States> = currentTab
        .flatMapLatest { tab ->
            when (tab) {
                TAB_ONE -> repo.getPosts()
                TAB_TWO -> repo.getFavoritedPosts()
            }.map { postsList ->
                MainState(
                    posts = postsList,
                    selectedTab =  tab
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = Loading
        )


    fun onEvent(event: Events) {
        viewModelScope.launch {
            when (event) {
                is Events.ChangeTab -> {
                    currentTab.value = event.tabNumber
                }

                is Events.DeletePost -> {
                    repo.setDeletedPost(event.post)
                }

                is Events.FavoritePost -> {
                    repo.setFavoritePost(event.post)
                }
            }
        }
    }

    sealed class States {
        data class MainState(
            val posts: List<PostsEntity> = emptyList(),
            val selectedTab: MainScreenTabId = TAB_ONE
        ) : States()
        object Loading : States()
    }

    sealed class Events {
        data class FavoritePost(val post: PostsEntity) : Events()
        data class DeletePost(val post: PostsEntity) : Events()
        data class ChangeTab(val tabNumber: MainScreenTabId) : Events()
    }

    enum class MainScreenTabId(val text: String, val icon: ImageVector) {
        TAB_ONE("Home", Default.Home),
        TAB_TWO("Favorite", Default.Favorite)
    }
}