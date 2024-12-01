package com.example.retrofitcomposeapp.data.repository

import com.example.retrofitcomposeapp.data.model.Post
import com.example.retrofitcomposeapp.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

class PostRepository {
    suspend fun getPosts(): Flow<Result<List<Post>>> = flow {
        emit(Result.Loading)
        try {
            val response = RetrofitClient.apiService.getPosts()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response"))
            } else {
                emit(Result.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPostById(id: Int): Flow<Result<Post>> = flow {
        emit(Result.Loading)
        try {
            val response = RetrofitClient.apiService.getPostById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response"))
            } else {
                emit(Result.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
}