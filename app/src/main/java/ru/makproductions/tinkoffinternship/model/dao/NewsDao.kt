package ru.makproductions.tinkoffinternship.model.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemLink
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemRegister

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    fun insert(newsItem: RoomNewsItem)

    @Insert(onConflict = REPLACE)
    fun insert(idRegister: RoomNewsItemRegister)

    @Insert(onConflict = REPLACE)
    fun insert(newsItems: List<RoomNewsItemLink>)

    @Update
    fun update(newsItem: RoomNewsItem)

    @Update
    fun update(idRegister: RoomNewsItemRegister)

    @Update
    fun update(newsItems: List<RoomNewsItemLink>)

    @Delete
    fun delete(newsItem: RoomNewsItem)

    @Delete
    fun delete(idRegister: RoomNewsItemRegister)

    @Delete
    fun delete(newsItems: List<RoomNewsItemLink>)

    @Query("SELECT * FROM roomnewsitem WHERE id = :id LIMIT 1")
    fun findNewsItem(id: Int): RoomNewsItem

    @Query("SELECT * FROM roomnewsitemlink")
    fun findNewsItems(): List<RoomNewsItemLink>

    @Query("SELECT * FROM roomnewsitemregister WHERE cachedItemId = :id LIMIT 1")
    fun findIdInRegister(id: Int): RoomNewsItemRegister
}