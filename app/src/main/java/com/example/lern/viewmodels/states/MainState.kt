package com.example.lern.viewmodels.states

import com.example.lern.data.local.entities.PostsEntity
import com.example.lern.viewmodels.states.TabId.TAB_ONE

data class MainState(
    val posts: List<PostsEntity> = emptyList(),
    var selectedTab: TabId = TAB_ONE
)
