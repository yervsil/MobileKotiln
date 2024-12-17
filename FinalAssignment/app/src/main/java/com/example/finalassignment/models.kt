package com.example.finalassignment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CartItem(
    val productId: Int,
    val name: String,
    val quantity: Int,
    val price: Double
)

data class Product(
    val productId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String = "",
    val category: String
)

data class Post(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String,

    @SerializedName("userId")
    val userId: Int
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
)

data class Review(
    val reviewId: Int,
    val productId: Int,
    val userId: Int,
    val rating: Int,
    val comment: String
)