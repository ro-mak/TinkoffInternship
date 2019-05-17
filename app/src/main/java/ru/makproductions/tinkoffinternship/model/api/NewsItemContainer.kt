package ru.makproductions.tinkoffinternship.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsItemContainer(
    @Expose @SerializedName("resultCode") var resultCode: String
    , @Expose @SerializedName("payload") var newsItem: NewsItem,
    @Expose @SerializedName("trackingId") var trackingId: String
)