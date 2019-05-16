package ru.makproductions.tinkoffinternship.model.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer
import ru.makproductions.tinkoffinternship.model.network.INetApi

class NewsRepo(private val iNetApi: INetApi) {
    fun loadNews(): Single<NewsListContainer> {
        return iNetApi.getNews().subscribeOn(Schedulers.io())
    }
}