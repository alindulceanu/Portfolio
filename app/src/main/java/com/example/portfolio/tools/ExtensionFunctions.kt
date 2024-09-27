package com.example.portfolio.tools

import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.data.remote.dto.PostResponse
import com.example.portfolio.viewmodels.states.DeletedPosts

fun PostsEntity.toDeletedPostsEntity(): DeletedPosts {
    return DeletedPosts(
        id = id,
        body = body,
        title = title,
    )
}

fun DeletedPosts.toPostsEntity(): PostsEntity {
    return PostsEntity(
        id = id,
        title = title,
        body = body,
        isDeleted = true
    )
}

fun PostResponse.toEntity(): PostsEntity {
    return PostsEntity(
        id = id,
        title = title,
        body = body
    )
}