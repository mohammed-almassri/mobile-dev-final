package com.example.notthefinal.core.network

import com.example.notthefinal.feature.posts.data.remote.api.JokeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private val retrofit: Lazy<Retrofit> = lazy {
        Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val jokeApiService: JokeApiService =
        retrofit.value.create(JokeApiService::class.java)
}