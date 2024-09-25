package com.example.lern.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.lern.data.MainRepository
import com.example.lern.tools.toDeletedPostsEntity
import com.example.lern.tools.toPostsEntity
import com.example.lern.viewmodels.events.Events.DeletedPostsScreenEvents
import com.example.lern.viewmodels.events.Events.DeletedPostsScreenEvents.CheckPost
import com.example.lern.viewmodels.events.Events.DeletedPostsScreenEvents.RestorePosts
import com.example.lern.viewmodels.states.States.DeletedPostsState
import com.example.lern.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletedPostsViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModelTemplate<DeletedPostsState, DeletedPostsScreenEvents>() {

    override val _uiState = MutableStateFlow(DeletedPostsState())

    init {
        initState()
    }

    override fun initState() {
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

    override fun onEvent(event: DeletedPostsScreenEvents) {
        when (event) {
            is CheckPost -> {
                viewModelScope.launch {
                    _uiState.emit(
                        _uiState.value.copy(
                            posts = _uiState.value.posts.map {
                                if (it.id == event.post.id) {
                                    it.copy(isChecked = !it.isChecked)
                                } else
                                    it
                            }
                        )
                    )
                }
            }

            RestorePosts -> {
                viewModelScope.launch {
                    _uiState.value.posts
                        .filter { it.isChecked }
                        .forEach {
                            repo.setDeletedPost(it.toPostsEntity())
                        }
                }
            }
        }
    }
}