package ru.makproductions.tinkoffinternship.model.network

import io.reactivex.Single
import retrofit2.http.GET
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer

interface INetApi {
    @GET("v1/news")
    fun getNews(): Single<NewsListContainer>
}