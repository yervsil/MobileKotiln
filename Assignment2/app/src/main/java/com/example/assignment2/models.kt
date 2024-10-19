package com.example.assignment2

data class Post(val id: Int, val username: String, val imageUrl: String, val caption: String, val likes: Int)
data class UserProfile(val username: String, val profilePictureUrl: String, val bio: String, val postCount: Int)
data class Notification(val id: Int, val content: String)
