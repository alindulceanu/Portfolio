package com.example.lern.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    var isDeleted: Boolean = false,
    var isFavorited: Boolean = false
)
