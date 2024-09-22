package com.example.lern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lern.data.MainRepository
import com.example.lern.data.remote.dto.PostRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    fun postEntity(title: String, body: String) {
        val request = PostRequest(1, title = title, body = body)
        viewModelScope.launch {
            withContext(IO) {
                repo.createPosts(request)
            }
        }
    }
}