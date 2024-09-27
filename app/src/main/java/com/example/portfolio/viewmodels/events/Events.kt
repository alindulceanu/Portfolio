package com.example.portfolio.viewmodels.events

import android.media.metrics.Event
import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.viewmodels.states.DeletedPosts
import com.example.portfolio.viewmodels.states.MainScreenTabId

sealed class Events {
    sealed class MainScreenEvents : Events() {
        data class FavoritePost(val post: PostsEntity) : MainScreenEvents()
        data class DeletePost(val post: PostsEntity) : MainScreenEvents()
        data class ChangeTab(val tabNumber: MainScreenTabId) : MainScreenEvents()
    }

    sealed class DeletedPostsScreenEvents : Events() {
        data class CheckPost(val post: DeletedPosts) : DeletedPostsScreenEvents()
        data object RestorePosts : DeletedPostsScreenEvents()
        data object RestoreAllPosts: DeletedPostsScreenEvents()
    }

    sealed class PostScreenEvents : Events() {
        data class PostEntity(val title: String, val body: String) : PostScreenEvents()
    }
}