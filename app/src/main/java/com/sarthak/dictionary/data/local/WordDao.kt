package com.sarthak.dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Word)

    @Query("DELETE FROM search_history where searchedWord = :searchedWord")
    suspend fun removeWord(searchedWord: String)

    @Query("SELECT searchedWord FROM search_history where searchedWord like :currentQuery ORDER BY timeStamp DESC LIMIT 6")
    fun getSearchHistory(currentQuery: String): Flow<List<String>>
}