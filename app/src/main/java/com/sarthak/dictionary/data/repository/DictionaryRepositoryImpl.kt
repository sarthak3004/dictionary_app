package com.sarthak.dictionary.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sarthak.dictionary.util.Result
import com.sarthak.dictionary.data.api.DictionaryApi
import com.sarthak.dictionary.data.mapper.toWordItem
import com.sarthak.dictionary.domain.model.WordItem
import com.sarthak.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi
): DictionaryRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getWordResult(wordQuery: String): Flow<Result<List<WordItem>>> = flow {
        emit(Result.Loading())
        val wordResultDto = try {
            dictionaryApi.getWordResult(wordQuery)
        } catch (e: HttpException) {
            emit(Result.Error(message = "HTTP Exception"))
            return@flow
        } catch (e: IOException) {
            emit(Result.Error(message = "IO Exception"))
            return@flow
        } catch (e: Exception) {
            emit(Result.Error(message = "Oh! Something went wrong!"))
            return@flow
        }

        val wordResult = wordResultDto.map { it.toWordItem() }
        emit(Result.Success(wordResult))
    }
}