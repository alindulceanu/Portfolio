package com.example.portfolio.ui.post_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.data.remote.dto.PostRequest
import com.example.portfolio.ui.post_screen.PostViewModel.PostScreenEvents.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

     fun onEvent(event: PostScreenEvents) {
        when (event) {
            is PostEntity -> {
                val request = PostRequest(1, title = event.title, body = event.body)
                viewModelScope.launch {
                    withContext(IO) {
                        repo.createPosts(request)
                    }
                }
            }
        }
    }

    sealed class PostScreenEvents {
        data class PostEntity(val title: String, val body: String) : PostScreenEvents()
    }
}