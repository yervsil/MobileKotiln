package com.example.finalassignment

import com.google.android.gms.common.api.Response
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import okhttp3.logging.HttpLoggingInterceptor
import com.example.finalassignment.Post

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

interface ProductService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}

object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val ProductService: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.finalassignment.ProductService::class.java)
    }
}
