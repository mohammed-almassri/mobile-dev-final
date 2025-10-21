package com.example.notthefinal.feature.posts.ui.state

import com.example.notthefinal.feature.posts.data.remote.dto.JokeResponseDto

data class JokessUiState(
    val isLoading:Boolean = false,
    val joke: JokeResponseDto? = null,
    val error: String? = null
){
    companion object {
        val Empty = JokessUiState()
    }
}

