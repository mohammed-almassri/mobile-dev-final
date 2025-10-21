package com.example.notthefinal.feature.items.domain.repository

import com.example.notthefinal.feature.items.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun insert(item: Item)
    suspend fun delete(item: Item)
    suspend fun update(item: Item)
    fun getAllItems(): Flow<List<Item>>
    fun getItemsByCategory(category:String): Flow<List<Item>>
    fun getItem(id: Int): Flow<Item>
    fun getCategories(): Flow<List<String>>
}