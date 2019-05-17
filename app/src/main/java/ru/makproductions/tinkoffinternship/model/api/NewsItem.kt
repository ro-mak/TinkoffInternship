package ru.makproductions.tinkoffinternship.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsItem(
    @Expose @SerializedName("title") var title: NewsItemLink,
    @Expose @SerializedName("creationDate") var creationDate: CreationDate,
    @Expose @SerializedName("lastModificationDate") var lastModificationDate: LastModificationDate,
    @Expose @SerializedName("content") var content: String,
    @Expose @SerializedName("bankInfoTypeId") var bankInfoTypeId: Int,
    @Expose @SerializedName("typeId") var typeId: String
)

data class CreationDate(@Expose @SerializedName("milliseconds") var millis: Long)
data class LastModificationDate(@Expose @SerializedName("milliseconds") var millis: Long)