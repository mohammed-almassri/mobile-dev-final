package com.example.notthefinal.feature.items.ui.state

data class AddItemUiState(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean? = null
)