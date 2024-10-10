package com.example.portfolio.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.viewmodels.DeletedPostsViewModel.Events.CheckPost
import com.example.portfolio.viewmodels.DeletedPostsViewModel.Events.RestoreAllPosts
import com.example.portfolio.viewmodels.DeletedPostsViewModel.Events.RestorePosts
import com.example.portfolio.viewmodels.DeletedPostsViewModel.States.DeletedPostsState
import com.example.portfolio.viewmodels.DeletedPostsViewModel.States.Loading
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
) : ViewModel() {

    private val _checkedPosts: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())

    val state: StateFlow<States> = repo.getDeletedPosts()
        .combine(_checkedPosts) { postsList, checkedPosts ->
            DeletedPostsState(
                posts = postsList,
                checkedPosts = checkedPosts
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = Loading
        )


    fun onEvent(event: Events) {
        if(state.value is DeletedPostsState) {
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
                        (state.value as DeletedPostsState).posts
                            .filter { it.id in checkedPosts }
                            .forEach { post ->
                                repo.setDeletedPost(post)
                            }
                        _checkedPosts.update { it - checkedPosts }
                    }
                }

                RestoreAllPosts -> {
                    viewModelScope.launch {
                        (state.value as DeletedPostsState).posts.forEach { post ->
                            repo.setDeletedPost(post)
                        }
                        _checkedPosts.value = emptySet()
                    }
                }
            }
        }
    }

    sealed class States {
        data class DeletedPostsState(
            val posts: List<PostsEntity> = emptyList(),
            val checkedPosts: Set<Int> = emptySet()
        ) : States()

        data object Loading : States()
    }

    sealed class Events {
        data class CheckPost(val post: PostsEntity) : Events()
        data object RestorePosts : Events()
        data object RestoreAllPosts : Events()
    }
}