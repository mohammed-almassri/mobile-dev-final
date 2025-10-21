package com.example.notthefinal.feature.items.ui.state

import com.example.notthefinal.feature.items.domain.model.Item

data class ItemListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<Item> = emptyList(),
    val category: String? = null,
)