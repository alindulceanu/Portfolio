package com.example.portfolio.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.CheckPost
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.RestoreAllPosts
import com.example.portfolio.viewmodels.events.Events.DeletedPostsScreenEvents.RestorePosts
import com.example.portfolio.viewmodels.states.States.DeletesPostsStates.DeletedPostsState
import com.example.portfolio.viewmodels.states.States.DeletesPostsStates.DeletedPostsUiState
import com.example.portfolio.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletedPostsViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModelTemplate<DeletedPostsScreenEvents>() {

    private val _checkedPosts: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())

    val state: StateFlow<DeletedPostsState> = repo.getDeletedPosts()
        .combine(_checkedPosts) { postsList, checkedPosts ->
            DeletedPostsState(
                posts = postsList,
                uiState = DeletedPostsUiState(
                    checkedPosts = checkedPosts
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = DeletedPostsState()
        )


    override fun onEvent(event: DeletedPostsScreenEvents) {
        when (event) {
            is CheckPost -> {
                viewModelScope.launch {
                    _checkedPosts.update { checkedPosts ->
                        if (event.post.id in checkedPosts) {
                            checkedPosts - event.post.id
                        } else {
                            checkedPosts + event.post.id
                        }
                    }
                }
            }

            RestorePosts -> {
                viewModelScope.launch {
                    val checkedPosts = _checkedPosts.value
                    state.value.posts
                        .filter { it.id in checkedPosts }
                        .forEach { post ->
                            repo.setDeletedPost(post)
                        }
                    _checkedPosts.update { it - checkedPosts }
                }
            }

            RestoreAllPosts -> {
                viewModelScope.launch {
                    state.value.posts.forEach { post ->
                        repo.setDeletedPost(post)
                    }
                    _checkedPosts.value = emptySet()
                }
            }
        }
    }

}