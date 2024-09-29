package com.example.portfolio.tools

import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.data.remote.dto.PostResponse

fun PostResponse.toEntity(): PostsEntity {
    return PostsEntity(
        id = id,
        title = title,
        body = body
    )
}