package com.example.retrofitcomposeapp.data.network

import com.example.retrofitcomposeapp.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): Response<Post>
}
