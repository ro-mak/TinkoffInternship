package ru.makproductions.tinkoffinternship.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import ru.makproductions.tinkoffinternship.model.repo.NewsRepo
import ru.makproductions.tinkoffinternship.view.NewsView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class NewsPresenter(val scheduler: Scheduler) : MvpPresenter<NewsView>() {
    @Inject
    lateinit var newsRepo: NewsRepo
    lateinit var disposable: Disposable
    fun loadNewsItem(id: Int) {
        disposable = newsRepo.loadNewsItem(id).observeOn(scheduler)
            .subscribe({ newsItemContainer -> viewState.onNewsItemLoaded(newsItemContainer.newsItem.content) },
                { Timber.e(it) })
    }
}