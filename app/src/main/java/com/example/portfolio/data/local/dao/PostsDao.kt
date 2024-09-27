package com.example.portfolio.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.portfolio.data.local.entities.PostsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostsDao {
    @Insert
    suspend fun insertPost(post: PostsEntity)

    @Insert
    suspend fun insertAllPosts(posts: List<PostsEntity>)

    @Delete
    suspend fun deletePost(post: PostsEntity)

    @Insert(onConflict = IGNORE)
    suspend fun updateDb(posts: List<PostsEntity>)

    @Update
    suspend fun updatePost(post: PostsEntity)

    @Query("SELECT * FROM PostsEntity WHERE isFavorited = 1")
    fun getFavoritedPosts(): Flow<List<PostsEntity>>

    @Query("SELECT * FROM PostsEntity WHERE isDeleted = 1")
    fun getDeletedPosts(): Flow<List<PostsEntity>>

    @Query("SELECT * FROM PostsEntity WHERE isDeleted = 0")
    fun getAvailablePosts(): Flow<List<PostsEntity>>
}