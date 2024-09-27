package com.example.portfolio.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.portfolio.data.local.dao.PostsDao
import com.example.portfolio.data.local.entities.PostsEntity

@Database(version = 1, entities = [PostsEntity::class])
abstract class PostsDatabase : RoomDatabase(){
    abstract fun postsDao(): PostsDao
}