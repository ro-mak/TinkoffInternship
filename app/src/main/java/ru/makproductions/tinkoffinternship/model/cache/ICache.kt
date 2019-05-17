package ru.makproductions.tinkoffinternship.model.cache

import io.reactivex.Single
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.api.NewsListContainer

interface ICache {
    fun saveNewsLinks(newsItemLinks: ArrayList<NewsItemLink>)
    fun loadNewsLinks(): Single<NewsListContainer>
}