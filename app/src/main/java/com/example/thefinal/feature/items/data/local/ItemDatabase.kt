package com.example.notthefinal.feature.items.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notthefinal.feature.items.domain.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase(){
    abstract fun itemDao(): ItemDao
    companion object{
        private var INSTANCE: ItemDatabase? = null
        fun getDatabase(context: Context): ItemDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                ItemDatabase::class.java,
                                "item_database"
                            ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}