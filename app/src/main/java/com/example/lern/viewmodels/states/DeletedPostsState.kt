package com.example.lern.viewmodels.states

import com.example.lern.data.local.entities.PostsEntity

data class DeletedPostsState(
    var posts: List<DeletedPosts> = emptyList()
)

data class DeletedPosts (
    val id: Int,
    val title: String,
    val body: String,
    var isChecked: Boolean = false
)

