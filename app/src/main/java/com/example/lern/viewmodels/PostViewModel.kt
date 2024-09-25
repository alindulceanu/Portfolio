package com.example.lern.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.lern.data.MainRepository
import com.example.lern.data.remote.dto.PostRequest
import com.example.lern.viewmodels.events.Events.PostScreenEvents
import com.example.lern.viewmodels.events.Events.PostScreenEvents.PostEntity
import com.example.lern.viewmodels.states.States.EmptyState
import com.example.lern.viewmodels.templates.ViewModelTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModelTemplate<EmptyState, PostScreenEvents>() {
    override val _uiState = MutableStateFlow(EmptyState)

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