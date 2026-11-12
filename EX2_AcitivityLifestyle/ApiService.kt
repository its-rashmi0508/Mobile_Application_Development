package com.example.sniffmeapp.data

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
