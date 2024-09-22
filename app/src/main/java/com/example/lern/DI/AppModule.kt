package com.example.lern.DI

import android.app.Application
import androidx.room.Room
import com.example.lern.data.MainRepository
import com.example.lern.data.local.dao.PostsDao
import com.example.lern.data.local.db.PostsDatabase
import com.example.lern.data.remote.PostsServiceImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostService(client: HttpClient) : PostsServiceImplementation {
        return PostsServiceImplementation(client)
    }

    @Provides
    @Singleton
    fun provideHttpClient() : HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideMainRepository(postsService: PostsServiceImplementation, postsDao: PostsDao) : MainRepository {
        return MainRepository(postsService = postsService, postsDao = postsDao)
    }

    @Provides
    @Singleton
    fun providePostsDatabase(context: Application) : PostsDatabase {
        return Room.databaseBuilder(
            context,
            PostsDatabase::class.java,
            "PostsDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostsDao(database: PostsDatabase): PostsDao {
        return database.postsDao()
    }
}