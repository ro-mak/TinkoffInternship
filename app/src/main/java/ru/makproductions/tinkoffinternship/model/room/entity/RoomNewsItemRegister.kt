package ru.makproductions.tinkoffinternship.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomNewsItemRegister(@PrimaryKey var cachedItemId: Int)