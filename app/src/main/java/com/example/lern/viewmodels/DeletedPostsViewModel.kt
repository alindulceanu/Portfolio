package com.example.lern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lern.data.MainRepository
import com.example.lern.tools.toDeletedPostsEntity
import com.example.lern.tools.toPostsEntity
import com.example.lern.viewmodels.states.DeletedPosts
import com.example.lern.viewmodels.states.DeletedPostsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletedPostsViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeletedPostsState())
    val uiState: StateFlow<DeletedPostsState> = _uiState

    init {
        initPosts()
    }

    private fun initPosts() {
        viewModelScope.launch {
            repo.getDeletedPosts()
                .collect {
                    _uiState.emit(
                        _uiState.value.copy(
                            posts = it.map { PostsEntity ->
                                PostsEntity.toDeletedPostsEntity()
                            }
                        )
                    )
                }
        }
    }

    fun checkPost(post: DeletedPosts) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    posts = _uiState.value.posts.map {
                        if (it.id == post.id) {
                            it.copy(isChecked = !it.isChecked)
                        }
                        else
                            it
                    }
                )
            )
        }
    }

    fun restorePosts() {
        viewModelScope.launch {
            _uiState.value.posts
                .filter { it.isChecked }
                .forEach {
                    repo.setDeletedPost(it.toPostsEntity())
            }
        }
    }
}