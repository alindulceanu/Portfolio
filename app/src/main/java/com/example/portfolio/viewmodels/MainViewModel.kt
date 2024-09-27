package com.example.portfolio.viewmodels

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.ChangeTab
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.DeletePost
import com.example.portfolio.viewmodels.events.Events.MainScreenEvents.FavoritePost
import com.example.portfolio.viewmodels.states.States.MainState
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_ONE
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_TWO
import com.example.portfolio.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
): ViewModelTemplate<MainState, MainScreenEvents>() {
    override val _uiState = MutableStateFlow(MainState())

    init {
        initState()
    }

    override fun initState() {
        d("MainViewModel", "Initialized posts")
        viewModelScope.launch {
            repo.getPosts().collect {
                _uiState.emit(
                    _uiState.value.copy(posts = it)
                )
            }
        }
    }

    override fun onEvent(event: MainScreenEvents) {
        when (event) {
            is ChangeTab -> {
                viewModelScope.launch {
                    d("MainViewModel", "Changing to ${event.tabNumber}")
                    when (event.tabNumber) {
                        TAB_ONE -> repo.getPosts()
                        TAB_TWO -> repo.getFavoritedPosts()
                    }.collect {
                        _uiState.emit(
                            _uiState.value.copy(
                                selectedTab = event.tabNumber,
                                posts = it
                            )
                        )
                    }
                }
            }

            is DeletePost -> {
                viewModelScope.launch {
                    repo.setDeletedPost(event.post)
                    if (event.post.isFavorited)
                        repo.setFavoritePost(event.post)
                }
            }

            is FavoritePost -> {
                viewModelScope.launch {
                    repo.setFavoritePost(event.post)
                }
            }
        }
    }
}