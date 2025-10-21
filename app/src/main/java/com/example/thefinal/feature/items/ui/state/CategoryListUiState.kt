package com.example.thefinal.feature.items.ui.state

import com.example.notthefinal.feature.items.domain.model.Item

data class CategoryListUiState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<String> = emptyList()
)