package ru.makproductions.tinkoffinternship.model.cache

import io.reactivex.Single
import ru.makproductions.tinkoffinternship.model.api.NewsItem
import ru.makproductions.tinkoffinternship.model.api.NewsItemContainer
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer

interface ICache {
    fun saveNewsLinks(newsItemLinks: ArrayList<NewsItemLink>)
    fun loadNewsLinks(): Single<NewsListContainer>
    fun loadNewsItem(id: Int): Single<NewsItemContainer>
    fun saveNewsItem(newsItem: NewsItem)
    fun saveIdToRegister(id: Int)
    fun isIdInRegister(id: Int): Single<Boolean>
}