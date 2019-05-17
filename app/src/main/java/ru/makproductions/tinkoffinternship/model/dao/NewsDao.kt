package ru.makproductions.tinkoffinternship.model.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    fun insert(newsItem: RoomNewsItem)

    @Insert(onConflict = REPLACE)
    fun insert(newsItems: List<RoomNewsItem>)

    @Update
    fun update(newsItem: RoomNewsItem)

    @Update
    fun update(newsItems: List<RoomNewsItem>)

    @Delete
    fun delete(newsItem: RoomNewsItem)

    @Delete
    fun delete(newsItems: List<RoomNewsItem>)

    @Query("SELECT * FROM roomnewsitem WHERE id = :id LIMIT 1")
    fun findNewsItem(id: Int): RoomNewsItem

    @Query("SELECT * FROM roomnewsitem")
    fun findNewsItems(): List<RoomNewsItem>
}