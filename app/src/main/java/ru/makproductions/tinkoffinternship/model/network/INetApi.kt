package ru.makproductions.tinkoffinternship.model.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.makproductions.tinkoffinternship.model.api.NewsItemContainer
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer

interface INetApi {
    @GET("v1/news")
    fun getNews(): Single<NewsListContainer>

    @GET("v1/news_content")
    fun getNewsItem(@Query("id") id: Int): Single<NewsItemContainer>
}