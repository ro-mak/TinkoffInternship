package ru.makproductions.tinkoffinternship.model.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.network.INetApi
import ru.makproductions.tinkoffinternship.utils.NetworkStatus

class NewsRepo(private val iNetApi: INetApi, private val cache: ICache) {
    fun loadNews(): Single<NewsListContainer> {
        if (NetworkStatus.isOnline) {
            return iNetApi.getNews().subscribeOn(Schedulers.io())
        } else {
            return cache.loadNewsLinks().subscribeOn(Schedulers.io())
        }
    }

    fun saveNews(newsItemLinks: ArrayList<NewsItemLink>) {
        cache.saveNewsLinks(newsItemLinks)
    }
}