package ru.makproductions.tinkoffinternship.model.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    fun insert(city: RoomNewsItem)

    @Update
    fun update(city: RoomNewsItem)

    @Delete
    fun delete(city: RoomNewsItem)

    @Query("SELECT * FROM roomnewsitem WHERE id = :id LIMIT 1")
    fun findNewsItem(id: Int): RoomNewsItem
}