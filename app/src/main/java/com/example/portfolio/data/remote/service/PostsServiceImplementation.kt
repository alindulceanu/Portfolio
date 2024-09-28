package com.example.portfolio.data.remote.service

import com.example.portfolio.data.remote.dto.PostRequest
import com.example.portfolio.data.remote.dto.PostResponse
import com.example.portfolio.tools.HttpRoutes.POSTS_URL
import com.example.portfolio.tools.InternetHelper.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import javax.inject.Inject

class PostsServiceImplementation @Inject constructor(
    private val client: HttpClient
) : PostsService {

    override suspend fun getPosts(): List<PostResponse> {
        return safeApiCall {
            client.get {
                url(POSTS_URL)
            }.body()
        } ?: emptyList()
    }

    @OptIn(InternalAPI::class)
    override suspend fun createPosts(postRequest: PostRequest): PostResponse? {
        return safeApiCall {
            client.post {
                url(POSTS_URL)
                contentType(Json)
                body = postRequest
            }.body()
        }
    }
}