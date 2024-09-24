package com.example.lern.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lern.viewmodels.states.DeletedPosts

@Entity
data class PostsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    var isDeleted: Boolean = false,
    var isFavorited: Boolean = false,
)
