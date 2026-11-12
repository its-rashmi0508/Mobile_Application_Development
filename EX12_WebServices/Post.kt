package com.example.sniffmeapp.data

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

data class PostResponse(
    val posts: List<Post>
)
