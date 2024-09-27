package com.example.portfolio.viewmodels.states

import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_ONE

sealed class States {
    data class MainState(
        val posts: List<PostsEntity> = emptyList(),
        val selectedTab: MainScreenTabId = TAB_ONE
    ) : States()

    data class DeletedPostsState(
        val posts: List<DeletedPosts> = emptyList()
    ) : States()

    data object EmptyState : States()
}


data class DeletedPosts (
    val id: Int,
    val title: String,
    val body: String,
    var isChecked: Boolean = false
)