package ru.makproductions.tinkoffinternship.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsItemLink(
    @Expose @SerializedName("id") var id: Int,
    @Expose @SerializedName("name") var name: String,
    @Expose @SerializedName("text") var text: String,
    @Expose @SerializedName("publicationDate") var publicationDate: PublicationDate,
    @Expose @SerializedName("bankInfoTypeId") var bankInfoTypeId: Int
)

data class PublicationDate(@Expose @SerializedName("milliseconds") var millis: Long)

