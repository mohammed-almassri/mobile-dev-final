package com.example.notthefinal.feature.items.data.repository


import com.example.notthefinal.feature.items.data.local.ItemDao
import com.example.notthefinal.feature.items.domain.model.Item
import com.example.notthefinal.feature.items.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao): ItemRepository {
    override suspend fun insert(item: Item) = itemDao.insert(item)

    override suspend fun delete(item: Item) = itemDao.delete(item)

    override suspend fun update(item: Item) = itemDao.update(item)

    override fun getAllItems(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItem(id: Int): Flow<Item> = itemDao.getItem(id)

    override fun getCategories(): Flow<List<String>> = itemDao.getCategories()
    override fun getItemsByCategory(category:String): Flow<List<Item>> = itemDao.getItemsByCategory(category)

}