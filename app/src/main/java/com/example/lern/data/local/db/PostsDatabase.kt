package com.example.lern.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lern.data.local.dao.PostsDao
import com.example.lern.data.local.entities.PostsEntity

@Database(version = 1, entities = [PostsEntity::class])
abstract class PostsDatabase : RoomDatabase(){
    abstract fun postsDao(): PostsDao
}