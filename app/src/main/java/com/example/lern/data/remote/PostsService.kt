package com.example.lern.data.remote

import com.example.lern.data.remote.dto.PostRequest
import com.example.lern.data.remote.dto.PostResponse

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    suspend fun createPosts(postRequest: PostRequest) : PostResponse?
}