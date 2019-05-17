package ru.makproductions.tinkoffinternship.presenter.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.repo.NewsRepo
import ru.makproductions.tinkoffinternship.navigation.Screens
import ru.makproductions.tinkoffinternship.view.list.NewsItemView
import ru.makproductions.tinkoffinternship.view.list.NewsListView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class NewsListPresenter(private val scheduler: Scheduler) : MvpPresenter<NewsListView>() {

    @Inject
    lateinit var newsRepo: NewsRepo

    fun loadNews() {
        disposables.add(
            newsRepo.loadNews().observeOn(scheduler).subscribe(
                { newsListContainer ->
                    run {
                        val newsItemLinks = (ArrayList(newsListContainer.newsItemLinks))
                        newsAdapterPresenterImpl.setNewsItemsList(newsItemLinks)
                        newsRepo.saveNews(newsItemLinks)
                    }
                },
                { Timber.e(it) })
        )
    }


    fun refreshNews() {
        disposables.add(
            newsRepo.refreshNews().observeOn(scheduler).subscribe(
                { newsListContainer ->
                    run {
                        val newsItemLinks = (ArrayList(newsListContainer.newsItemLinks))
                        newsAdapterPresenterImpl.setNewsItemsList(newsItemLinks)
                        newsRepo.saveNews(newsItemLinks)
                    }
                },
                { Timber.e(it) })
        )
    }

    private var disposables: ArrayList<Disposable>

    @Inject
    lateinit var router: Router
    val newsAdapterPresenterImpl: NewsAdapterPresenter


    init {
        this.newsAdapterPresenterImpl = NewsAdapterPresenterImpl()
        this.disposables = ArrayList()
    }


    inner class NewsAdapterPresenterImpl : NewsAdapterPresenter {
        private var clickSubjects = ArrayList<PublishSubject<NewsItemView>>()
        private var listItems = ArrayList<NewsItemLink>()
        private fun createPublishSubjects() {
            if (clickSubjects.size == 0) {
                val elementsToUpdate = getListCount()
                for (i in 0 until elementsToUpdate) {
                    clickSubjects.add(PublishSubject.create<NewsItemView>())
                }
            }
        }


        override fun getClickSubject(position: Int): Observer<NewsItemView> {
            return clickSubjects.get(position)
        }

        override fun getListCount(): Int {
            return listItems.size
        }

        override fun bindView(newsItemView: NewsItemView) {
            val position: Int = newsItemView.pos
            Timber.e("ListPresenter bindView position = " + position)
            val newsItemLink = listItems.get(position)
            newsItemView.setText(newsItemLink.text)
            disposables.add(
                clickSubjects.get(position).subscribe(
                    { clickSubjectsNewsItemView ->
                        Timber.e("Clicked on =" + position)
                        router.navigateTo(Screens.Companion.NewsScreen())
                    },
                    { Timber.e(it) })
            )
        }

        override fun setNewsItemsList(newsItems: ArrayList<NewsItemLink>) {
            Timber.e("setNewsItemsList")
            this.listItems = newsItems
            createPublishSubjects()
            viewState.onNewsLoaded()
        }
    }

    override fun onDestroy() {
        Timber.e("OnDestroy")
        super.onDestroy()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }
}