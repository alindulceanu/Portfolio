package com.example.lern.viewmodels.events

import com.example.lern.data.local.entities.PostsEntity
import com.example.lern.viewmodels.states.DeletedPosts
import com.example.lern.viewmodels.states.TabId

sealed class Events {
    sealed class MainScreenEvents : Events() {
        data class FavoritePost(val post: PostsEntity) : MainScreenEvents()
        data class DeletePost(val post: PostsEntity) : MainScreenEvents()
        data class ChangeTab(val tabNumber: TabId) : MainScreenEvents()
    }

    sealed class DeletedPostsScreenEvents : Events() {
        data class CheckPost(val post: DeletedPosts) : DeletedPostsScreenEvents()
        data object RestorePosts : DeletedPostsScreenEvents()
    }

    sealed class PostScreenEvents : Events() {
        data class PostEntity(val title: String, val body: String) : PostScreenEvents()
    }
}