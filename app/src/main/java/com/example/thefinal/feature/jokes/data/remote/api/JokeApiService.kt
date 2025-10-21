package com.example.notthefinal.feature.posts.data.remote.api

import com.example.notthefinal.feature.posts.data.remote.dto.JokeResponseDto
import retrofit2.http.GET

interface JokeApiService {
    @GET("random_joke")
    suspend fun fetchJoke(): JokeResponseDto
}