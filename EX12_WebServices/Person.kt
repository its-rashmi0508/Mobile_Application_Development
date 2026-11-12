package com.example.sniffmeapp.data

import com.google.gson.annotations.SerializedName

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val gender: String,
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("eye_color")
    val eyeColor: String
)

data class SwapiResponse(
    val count: Int,
    val results: List<Person>
)
