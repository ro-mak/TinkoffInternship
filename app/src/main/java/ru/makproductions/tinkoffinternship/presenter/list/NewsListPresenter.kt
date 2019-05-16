package ru.makproductions.tinkoffinternship.presenter.list

import io.reactivex.Observer
import ru.makproductions.tinkoffinternship.model.api.NewsItem
import ru.makproductions.tinkoffinternship.view.list.NewsItemView

interface NewsListPresenter {
    fun getListCount(): Int
    fun bindView(newsItemView: NewsItemView)
    fun getClickSubject(position: Int): Observer<NewsItemView>
    fun setNewsItemsList(newsItems: ArrayList<NewsItem>)
}