package com.example.lern.data.remote.service

import com.example.lern.data.remote.dto.PostRequest
import com.example.lern.data.remote.dto.PostResponse

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    suspend fun createPosts(postRequest: PostRequest) : PostResponse?
}