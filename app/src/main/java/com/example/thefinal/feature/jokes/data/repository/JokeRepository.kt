package com.example.notthefinal.feature.posts.data.repository

import com.example.notthefinal.feature.posts.data.remote.dto.JokeResponseDto

interface JokeRepository {
    suspend fun fetchJoke(): JokeResponseDto
}