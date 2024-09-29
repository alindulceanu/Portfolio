package com.example.portfolio.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.ChangeTab
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.DeletePost
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.FavoritePost
import com.example.portfolio.viewmodels.states.MainScreenTabId
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_ONE
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_TWO
import com.example.portfolio.viewmodels.states.States.MainStates.MainState
import com.example.portfolio.viewmodels.states.States.MainStates.MainUiState
import com.example.portfolio.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModelTemplate<MainScreenEvents>() {

    private val currentTab: MutableStateFlow<MainScreenTabId> = MutableStateFlow(TAB_ONE)

    val state: StateFlow<MainState> = currentTab
        .flatMapLatest { tab ->
            when (tab) {
                TAB_ONE -> repo.getPosts()
                TAB_TWO -> repo.getFavoritedPosts()
            }.map { postsList ->
                MainState(
                    posts = postsList,
                    uiState = MainUiState(selectedTab = tab)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = MainState()
        )


    override fun onEvent(event: MainScreenEvents) {
        viewModelScope.launch {
            when (event) {
                is ChangeTab -> {
                    currentTab.value = event.tabNumber
                }

                is DeletePost -> {
                    repo.setDeletedPost(event.post)
                    if (event.post.isFavorited)
                        repo.setFavoritePost(event.post)
                }

                is FavoritePost -> {
                    repo.setFavoritePost(event.post)
                }
            }
        }
    }
}