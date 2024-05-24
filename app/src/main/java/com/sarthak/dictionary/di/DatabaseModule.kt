package com.sarthak.dictionary.di

import android.content.Context
import androidx.room.Room
import com.sarthak.dictionary.data.local.SearchHistoryDatabase
import com.sarthak.dictionary.data.local.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchHistoryDatabase {
        return Room.databaseBuilder(
            context,
            SearchHistoryDatabase::class.java,
            "search_history_database"
        ).build()
    }

    @Provides
    fun provideWordDao(database: SearchHistoryDatabase): WordDao {
        return database.wordDao()
    }
}