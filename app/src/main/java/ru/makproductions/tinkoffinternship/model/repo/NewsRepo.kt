package ru.makproductions.tinkoffinternship.model.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.NewsItem
import ru.makproductions.tinkoffinternship.model.api.NewsItemContainer
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.network.INetApi
import ru.makproductions.tinkoffinternship.utils.NetworkStatus
import timber.log.Timber

class NewsRepo(private val iNetApi: INetApi, private val cache: ICache) {
    var isListCached: Boolean = false
    fun loadNews(): Single<NewsListContainer> {
        Timber.e("Loading... Is it cached? " + if (isListCached) "Yes" else "No")
        if (!NetworkStatus.isOnline || isListCached) {
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
        isListCached = true
    }

    fun loadNewsItem(id: Int): Single<NewsItemContainer> {
        val isCached = cache.isIdInRegister(id).subscribeOn(Schedulers.io()).blockingGet()
        if (!isCached) {
            Timber.e("item with id = " + id + " not cached")
            return iNetApi.getNewsItem(id).subscribeOn(Schedulers.io())
        } else {
            Timber.e("item with id = " + id + " cached. Loading...")
            return cache.loadNewsItem(id).subscribeOn(Schedulers.io())
        }
    }

    fun saveNewsItem(newsItem: NewsItem) {
        cache.saveNewsItem(newsItem)
        cache.saveIdToRegister(newsItem.title.id)
    }
}