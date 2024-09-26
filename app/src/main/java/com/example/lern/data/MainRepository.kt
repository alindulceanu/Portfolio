package com.example.lern.data

import com.example.lern.data.local.dao.PostsDao
import com.example.lern.data.local.entities.PostsEntity
import com.example.lern.data.remote.service.PostsServiceImplementation
import com.example.lern.data.remote.dto.PostRequest
import com.example.lern.data.remote.dto.PostResponse
import com.example.lern.tools.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val postsService: PostsServiceImplementation,
    private val postsDao: PostsDao
) {
    init {
        CoroutineScope(IO).launch {
            postsDao.updateDb(
                postsService.getPosts().map {
                    it.toEntity()
                })
        }
    }

    fun getFavoritedPosts(): Flow<List<PostsEntity>> {
        return postsDao.getFavoritedPosts()
    }

    fun getDeletedPosts(): Flow<List<PostsEntity>> {
        return postsDao.getDeletedPosts()
    }

    suspend fun setFavoritePost(post: PostsEntity) {
        postsDao.updatePost(post.apply { isFavorited = !isFavorited })
    }

    suspend fun setDeletedPost(post: PostsEntity) {
        postsDao.updatePost(post.apply { isDeleted = !isDeleted })
    }

    fun getPosts(): Flow<List<PostsEntity>> {
        return postsDao.getAvailablePosts()
    }

    suspend fun createPosts(postRequest: PostRequest): PostResponse? {
        return postsService.createPosts(postRequest)
    }
}