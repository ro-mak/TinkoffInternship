package ru.makproductions.tinkoffinternship.model.room

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer
import ru.makproductions.tinkoffinternship.model.api.PublicationDate
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.db.NewsDatabase
import ru.makproductions.tinkoffinternship.model.room.entity.RoomNewsItem
import timber.log.Timber

class RoomCache : ICache {
    override fun saveNewsLinks(newsItemLinks: ArrayList<NewsItemLink>) {
        Timber.e("saving to cache")
        val roomNewsItems = ArrayList<RoomNewsItem>()
        for (i in 0 until newsItemLinks.size) {
            val link = newsItemLinks[i]
            val item = RoomNewsItem(link.id, link.name, link.text, link.publicationDate.millis, link.bankInfoTypeId)
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
}