package ru.makproductions.tinkoffinternship.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomNewsItemLink(
    @PrimaryKey var id: Int, var name: String,
    var text: String,
    var publicationDate: Long,
    var bankInfoTypeId: Int
)