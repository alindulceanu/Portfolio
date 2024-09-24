package com.example.lern.viewmodels

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lern.data.MainRepository
import com.example.lern.data.local.entities.PostsEntity
import com.example.lern.viewmodels.states.MainState
import com.example.lern.viewmodels.states.TabId
import com.example.lern.viewmodels.states.TabId.TAB_ONE
import com.example.lern.viewmodels.states.TabId.TAB_TWO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState

    init {
        initPosts()
    }

    private fun initPosts() {
        d("MainViewModel", "Initialized posts")
        viewModelScope.launch {
            repo.getPosts().collect {
                _uiState.emit(
                    _uiState.value.copy(posts = it)
                )
            }
        }
    }

    fun deletePost(post: PostsEntity) {
        viewModelScope.launch {
            repo.setDeletedPost(post)
            if (post.isFavorited)
                repo.setFavoritePost(post)
        }
    }

    fun favoritePost(post: PostsEntity) {
        viewModelScope.launch {
            repo.setFavoritePost(post)
        }
    }

    fun getDeletedPosts() {
        viewModelScope.launch {
            repo.getDeletedPosts().collect {
                _uiState.emit(
                    _uiState.value.copy(posts = it)
                )
            }
        }
    }

    fun changeTab(tabNumber: TabId) {
        viewModelScope.launch {
            d("MainViewModel","Changing to $tabNumber")
            when(tabNumber) {
                TAB_ONE -> repo.getPosts()
                TAB_TWO -> repo.getFavoritedPosts()
            }.collect {
                _uiState.emit(
                    _uiState.value.copy(
                        selectedTab = tabNumber,
                        posts = it
                    )
                )
            }
        }
    }
}