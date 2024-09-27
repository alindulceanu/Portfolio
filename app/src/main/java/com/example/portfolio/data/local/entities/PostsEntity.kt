package com.example.portfolio.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val isDeleted: Boolean = false,
    val isFavorited: Boolean = false,
)
