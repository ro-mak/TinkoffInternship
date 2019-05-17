package ru.makproductions.tinkoffinternship.model.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.network.INetApi
import ru.makproductions.tinkoffinternship.utils.NetworkStatus
import timber.log.Timber

class NewsRepo(private val iNetApi: INetApi, private val cache: ICache) {
    var isCached: Boolean = false
    fun loadNews(): Single<NewsListContainer> {
        Timber.e("Loading... Is it cached? " + if (isCached) "Yes" else "No")
        if (!NetworkStatus.isOnline || isCached) {
            return cache.loadNewsLinks().subscribeOn(Schedulers.io())
        } else {
            return iNetApi.getNews().subscribeOn(Schedulers.io())
        }
    }

    fun refreshNews(): Single<NewsListContainer> {
        return iNetApi.getNews().subscribeOn(Schedulers.io())
    }

    fun saveNews(newsItemLinks: ArrayList<NewsItemLink>) {
        cache.saveNewsLinks(newsItemLinks)
        isCached = true
    }
}