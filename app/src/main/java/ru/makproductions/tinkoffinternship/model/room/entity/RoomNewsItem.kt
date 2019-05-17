package ru.makproductions.tinkoffinternship.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.makproductions.tinkoffinternship.model.api.CreationDate
import ru.makproductions.tinkoffinternship.model.api.LastModificationDate

@Entity
data class RoomNewsItem(
    @PrimaryKey
    var id: Int,
    var creationDate: CreationDate,
    var lastModificationDate: LastModificationDate,
    var content: String,
    var bankInfoTypeId: Int,
    var typeId: String
)