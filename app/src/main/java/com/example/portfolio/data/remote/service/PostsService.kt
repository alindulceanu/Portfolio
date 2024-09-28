package com.example.portfolio.data.remote.service

import com.example.portfolio.data.remote.dto.PostRequest
import com.example.portfolio.data.remote.dto.PostResponse

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    suspend fun createPosts(postRequest: PostRequest): PostResponse?
}