package com.example.portfolio.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.portfolio.data.MainRepository
import com.example.portfolio.data.remote.dto.PostRequest
import com.example.portfolio.viewmodels.events.Events.PostScreenEvents
import com.example.portfolio.viewmodels.events.Events.PostScreenEvents.PostEntity
import com.example.portfolio.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModelTemplate<PostScreenEvents>() {

    override fun onEvent(event: PostScreenEvents) {
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
}