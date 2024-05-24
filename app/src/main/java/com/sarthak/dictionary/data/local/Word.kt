package com.sarthak.dictionary.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    val searchedWord: String,
    @NonNull
    val timeStamp: Long
)