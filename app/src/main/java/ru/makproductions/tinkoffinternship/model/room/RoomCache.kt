package ru.makproductions.tinkoffinternship.model.room

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.*
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.db.NewsDatabase
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemLink
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItemRegister
import timber.log.Timber

class RoomCache : ICache {
    override fun saveNewsLinks(newsItemLinks: ArrayList<NewsItemLink>) {
        Timber.e("saving to cache")
        val roomNewsItems = ArrayList<RoomNewsItemLink>()
        for (i in 0 until newsItemLinks.size) {
            val link = newsItemLinks[i]
            val item = RoomNewsItemLink(link.id, link.name, link.text, link.publicationDate.millis, link.bankInfoTypeId)
            roomNewsItems.add(item)
        }
        Completable.create({ emitter ->
            NewsDatabase.getInstance()?.getNewsDao()?.insert(roomNewsItems)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io()).subscribe()

    }

    override fun loadNewsLinks(): Single<NewsListContainer> {
        Timber.e("loading from cache")
        return Single.create({ emitter ->
            val roomNewsItemsList = NewsDatabase.getInstance()?.getNewsDao()?.findNewsItems()
            val newsItemLinks = ArrayList<NewsItemLink>()
            roomNewsItemsList?.let {
                for (i in 0 until roomNewsItemsList.size) {
                    val item = roomNewsItemsList.get(i)
                    item.let {
                        val link = NewsItemLink(
                            item.id,
                            item.name,
                            item.text,
                            PublicationDate(item.publicationDate),
                            item.bankInfoTypeId
                        )
                        newsItemLinks.add(link)
                    }
                }
                val container = NewsListContainer(
                    "resultCode is not cached",
                    newsItemLinks,
                    "id is not cached"
                )
                container.let { emitter.onSuccess(container) }
            }
        })
    }

    override fun loadNewsItem(id: Int): Single<NewsItemContainer> {
        Timber.e("loading news item from cache")
        return Single.create({ emitter ->
            val roomItem = NewsDatabase.getInstance()?.getNewsDao()?.findNewsItem(id)
            roomItem?.let {
                val newsItem = NewsItem(
                    NewsItemLink(roomItem.id, "", "", PublicationDate(0), 0),
                    roomItem.creationDate,
                    roomItem.lastModificationDate,
                    roomItem.content,
                    roomItem.bankInfoTypeId,
                    roomItem.typeId
                )
                val container = NewsItemContainer("resultCode is not cached", newsItem, "trackingId is not cached")
                emitter.onSuccess(container)
            }
        })
    }

    override fun saveNewsItem(newsItem: NewsItem) {
        Timber.e("saving news item " + newsItem.title.id + " to cache")
        val item = RoomNewsItem(
            newsItem.title.id,
            newsItem.creationDate,
            newsItem.lastModificationDate,
            newsItem.content,
            newsItem.bankInfoTypeId,
            newsItem.typeId
        )
        Completable.create({ emitter ->
            NewsDatabase.getInstance()?.getNewsDao()?.insert(item)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io()).subscribe()
    }

    override fun saveIdToRegister(id: Int) {
        Timber.e("id " + id + " saved to register")
        Completable.create({ emitter ->
            val register = RoomNewsItemRegister(id)
            NewsDatabase.getInstance()?.getNewsDao()?.insert(register)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io()).subscribe()
    }

    override fun isIdInRegister(id: Int): Single<Boolean> {
        Timber.e("Searching id in register")
        return Single.create({ emitter ->
            val cachedId = NewsDatabase.getInstance()?.getNewsDao()?.findIdInRegister(id)?.cachedItemId
            Timber.e("cached id = " + cachedId)
            emitter.onSuccess(cachedId == id)
        }
        )
    }
}