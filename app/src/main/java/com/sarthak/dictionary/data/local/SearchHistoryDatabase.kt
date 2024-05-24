package com.sarthak.dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class SearchHistoryDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}
