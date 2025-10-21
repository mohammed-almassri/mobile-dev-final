package com.example.notthefinal.feature.items.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notthefinal.feature.items.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    @Delete
    suspend fun delete(item: Item)
    @Update
    suspend fun update(item: Item)
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("Select distinct category from items")
    fun getCategories(): Flow<List<String>>

    @Query("SELECT * FROM items WHERE category = :category")
    fun getItemsByCategory(category:String): Flow<List<Item>>
}