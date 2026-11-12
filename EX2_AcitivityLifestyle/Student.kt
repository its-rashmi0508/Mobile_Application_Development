package com.example.sniffmeapp.data

data class Student(
    val id: Int,
    val name: String,
    val course: String,
    val email: String
)

data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)
