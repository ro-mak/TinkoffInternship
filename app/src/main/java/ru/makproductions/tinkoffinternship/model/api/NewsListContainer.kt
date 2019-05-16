package ru.makproductions.tinkoffinternship.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsListContainer(
    @Expose @SerializedName("resultCode") var resultCode: String
    , @Expose @SerializedName("payload") var newsItemLinks: List<NewsItemLink>,
    @Expose @SerializedName("trackingId") var trackingId: String
)