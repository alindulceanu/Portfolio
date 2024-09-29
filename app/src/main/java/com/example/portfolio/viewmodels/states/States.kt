package com.example.portfolio.viewmodels.states

import com.example.portfolio.data.local.entities.PostsEntity
import com.example.portfolio.viewmodels.states.MainScreenTabId.TAB_ONE

sealed class States {
    sealed class MainStates : States() {
        data class MainState(
            val posts: List<PostsEntity> = emptyList(),
            val uiState: MainUiState = MainUiState()
        ) : MainStates()

        data class MainUiState(
            val selectedTab: MainScreenTabId = TAB_ONE
        ) : MainStates()
    }

    sealed class DeletesPostsStates : States() {
        data class DeletedPostsState(
            val posts: List<PostsEntity> = emptyList(),
            val uiState: DeletedPostsUiState = DeletedPostsUiState()
        ) : DeletesPostsStates()

        data class DeletedPostsUiState(
            val checkedPosts: Set<Int> = emptySet()
        ) : DeletesPostsStates()
    }

    data object EmptyState : States()
}