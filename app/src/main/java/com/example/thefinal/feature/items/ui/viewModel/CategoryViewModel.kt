package com.example.thefinal.feature.items.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notthefinal.feature.items.domain.repository.ItemRepository
import com.example.thefinal.feature.items.ui.state.CategoryListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(private val itemRepository: ItemRepository): ViewModel() {
    private val _categoryListUiState = MutableStateFlow(CategoryListUiState(isLoading = false))
    val categoryListUiState: StateFlow<CategoryListUiState> = _categoryListUiState.asStateFlow()

    init {
        viewModelScope.launch {
            itemRepository.getCategories()
                .distinctUntilChanged()
                .onStart {
                    _categoryListUiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch {e->
                    _categoryListUiState.update {
                        it.copy(isLoading = false, error = e.message)
                    }
                }
                .collect { items ->
                    _categoryListUiState.update {
                        it.copy(isLoading = false, error = null, items = items)
                    }
                }
        }
    }
}