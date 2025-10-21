package com.example.notthefinal.feature.posts.data.remote.dto

data class JokeResponseDto(
    val id: Long,
    val setup: String,
    val punchline: String,
    val type: String
)
