package com.example.lern.data.remote

import com.example.lern.data.remote.HttpRoutes.POSTS_URL
import com.example.lern.data.remote.dto.PostRequest
import com.example.lern.data.remote.dto.PostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import javax.inject.Inject

class PostsServiceImplementation @Inject constructor(
    private val client: HttpClient
) : PostsService {

    override suspend fun getPosts(): List<PostResponse> {
        return client.get {
            url(POSTS_URL)
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun createPosts(postRequest: PostRequest): PostResponse? {
        return try {
            client.post {
                url(POSTS_URL)
                contentType(Json)
                body = postRequest
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}