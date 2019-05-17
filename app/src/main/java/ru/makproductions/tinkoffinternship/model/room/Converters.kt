package ru.makproductions.tinkoffinternship.model.room

import android.arch.persistence.room.TypeConverter
import ru.makproductions.tinkoffinternship.model.api.CreationDate
import ru.makproductions.tinkoffinternship.model.api.LastModificationDate
import ru.makproductions.tinkoffinternship.model.api.PublicationDate

class Converters {

    @TypeConverter
    fun creationDateToLong(creationDate: CreationDate): Long {
        return creationDate.millis
    }

    @TypeConverter
    fun LongToCreationDate(creationDateMillis: Long): CreationDate {
        return CreationDate(creationDateMillis)
    }

    @TypeConverter
    fun LongToPublicationDate(publicationDateMillis: Long): PublicationDate {
        return PublicationDate(publicationDateMillis)
    }

    @TypeConverter
    fun publicationDateToLong(publicationDate: PublicationDate): Long {
        return publicationDate.millis

    }

    @TypeConverter
    fun LongToLastModificationDate(lastModificationDateMillis: Long): LastModificationDate {
        return LastModificationDate(lastModificationDateMillis)
    }

    @TypeConverter
    fun lastModificationDateToLong(lastModificationDate: LastModificationDate): Long {
        return lastModificationDate.millis
    }


}