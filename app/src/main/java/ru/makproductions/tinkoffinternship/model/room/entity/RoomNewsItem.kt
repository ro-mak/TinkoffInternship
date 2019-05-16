package ru.makproductions.tinkoffinternship.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomNewsItem(@PrimaryKey var id: Int, var name: String, var text: String)