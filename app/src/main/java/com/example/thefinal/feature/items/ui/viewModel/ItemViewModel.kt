package com.example.notthefinal.feature.items.ui.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notthefinal.feature.items.domain.model.Item
import com.example.notthefinal.feature.items.domain.repository.ItemRepository
import com.example.notthefinal.feature.items.ui.state.AddItemUiState
import com.example.notthefinal.feature.items.ui.state.ItemListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemViewModel constructor(private val itemRepository: ItemRepository): ViewModel() {
    private val _itemListUiState = MutableStateFlow(ItemListUiState(isLoading = false))
    val itemListUiState: StateFlow<ItemListUiState> = _itemListUiState.asStateFlow()

    private val _addItemUiState = MutableStateFlow(AddItemUiState())
    val addItemUiState = _addItemUiState.asStateFlow()

    private val _currentItem = MutableStateFlow<Item?>(null)
    val currentItem = _currentItem.asStateFlow()

    init {
        if(_itemListUiState.value.category!=null){
            viewModelScope.launch {
                itemRepository.getItemsByCategory(_itemListUiState.value.category!!)
                    .distinctUntilChanged()
                    .onStart {
                        _itemListUiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    .catch {e->
                        _itemListUiState.update {
                            it.copy(isLoading = false, error = e.message)
                        }
                    }
                    .collect { items ->
                        _itemListUiState.update {
                            it.copy(isLoading = false, error = null, items = items)
                        }
                    }
            }
        }
        else
        viewModelScope.launch {
            itemRepository.getAllItems()
                .distinctUntilChanged()
                .onStart {
                    _itemListUiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch {e->
                    _itemListUiState.update {
                        it.copy(isLoading = false, error = e.message)
                    }
                }
                .collect { items ->
                    _itemListUiState.update {
                        it.copy(isLoading = false, error = null, items = items)
                    }
                }
        }
    }

    fun setCurrentItem(item: Item) {
        _currentItem.value = item
    }

    fun setCategory(category: String?){
        _itemListUiState.update { it.copy(
            category = category
        ) }
        if(category!=null){
            viewModelScope.launch {
                itemRepository.getItemsByCategory(category)
                    .distinctUntilChanged()
                    .onStart {
                        _itemListUiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    .catch {e->
                        _itemListUiState.update {
                            it.copy(isLoading = false, error = e.message)
                        }
                    }
                    .collect { items ->
                        _itemListUiState.update {
                            it.copy(isLoading = false, error = null, items = items)
                        }
                    }
            }
        }
    }

    fun editItem(item: Item) {
        viewModelScope.launch {
            itemRepository.update(item)
        }
    }
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemRepository.delete(item)
        }
    }
    fun addItem(name: String, category: String, price: String, quantity: String) {

        val priceVal = price.toDoubleOrNull()
        val qtyVal = quantity.toIntOrNull()
        if (name.isBlank() || priceVal == null || qtyVal == null) {
            _addItemUiState.update { it.copy(error = "Enter name, valid price, and valid quantity.") }
            return
        }
        viewModelScope.launch {
            runCatching {
                _addItemUiState.update {
                    it.copy(isLoading = true)
                }
                itemRepository.insert(Item(name = name, category = category, price = price.toDouble(), quantity = quantity.toInt()))
                _addItemUiState.update {
                    it.copy(success = true, isLoading = false)
                }
            }.onFailure {exception ->
                _addItemUiState.update {
                    it.copy(success = false, error = exception.message, isLoading = false)
                }
            }
        }
    }

    fun clearError() {
        _addItemUiState.update {
            it.copy(error = null)
        }
    }

    fun consumeSuccess() {
        _addItemUiState.update {
            it.copy(success = null)
        }
    }

}