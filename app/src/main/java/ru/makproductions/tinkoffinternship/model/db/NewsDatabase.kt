package ru.makproductions.tinkoffinternship.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.makproductions.tinkoffinternship.model.dao.NewsDao
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem

@Database(entities = [RoomNewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        private val DB_NAME = "NewsDatabase.db"
        fun create(context: Context) {
            instance = Room.databaseBuilder<NewsDatabase>(context, NewsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration().build()

        }

        @Volatile
        private lateinit var instance: NewsDatabase

        @Synchronized
        fun getInstance(): NewsDatabase? {
            instance.let {
                return it
            }
            throw NullPointerException("Database has not been created. Please call create()")
        }
    }


    abstract fun getNewsDao(): NewsDao
}