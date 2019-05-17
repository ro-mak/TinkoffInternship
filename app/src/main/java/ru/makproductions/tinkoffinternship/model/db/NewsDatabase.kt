package ru.makproductions.tinkoffinternship.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ru.makproductions.tinkoffinternship.model.dao.NewsDao
import ru.makproductions.tinkoffinternship.model.room.Converters
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemLink
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemRegister

@Database(
    entities = [RoomNewsItemLink::class, RoomNewsItem::class, RoomNewsItemRegister::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
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