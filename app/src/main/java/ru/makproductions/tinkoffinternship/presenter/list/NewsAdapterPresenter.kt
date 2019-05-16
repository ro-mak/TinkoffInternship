package ru.makproductions.tinkoffinternship.presenter.list

import io.reactivex.Observer
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.view.list.NewsItemView

interface NewsAdapterPresenter {
    fun getListCount(): Int
    fun bindView(newsItemView: NewsItemView)
    fun getClickSubject(position: Int): Observer<NewsItemView>
    fun setNewsItemsList(newsItems: ArrayList<NewsItemLink>)
}