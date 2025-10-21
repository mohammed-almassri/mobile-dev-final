package com.example.notthefinal.feature.posts.data.repository.impl

import com.example.notthefinal.feature.posts.data.remote.api.JokeApiService
import com.example.notthefinal.feature.posts.data.remote.dto.JokeResponseDto
import com.example.notthefinal.feature.posts.data.repository.JokeRepository

class JokeRepositoryImpl(val jokeApiService: JokeApiService): JokeRepository {
    override suspend fun fetchJoke(): JokeResponseDto {
        return jokeApiService.fetchJoke()
    }
}