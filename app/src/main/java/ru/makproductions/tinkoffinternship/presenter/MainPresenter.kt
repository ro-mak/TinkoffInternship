package ru.makproductions.tinkoffinternship.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import ru.makproductions.tinkoffinternship.model.api.NewsItem
import ru.makproductions.tinkoffinternship.navigation.Screens
import ru.makproductions.tinkoffinternship.presenter.list.NewsListPresenter
import ru.makproductions.tinkoffinternship.view.list.NewsItemView
import ru.makproductions.tinkoffinternship.view.main.MainView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainPresenter(scheduler: Scheduler) : MvpPresenter<MainView>() {

    private lateinit var disposables: ArrayList<Disposable>

    @Inject
    lateinit var router: Router
    private val newsListPresenterImpl: NewsListPresenter

    init {
        this.newsListPresenterImpl = NewsListPresenterImpl()
        this.disposables = ArrayList()
    }

    fun getNewsListPresenter(): NewsListPresenter {
        return newsListPresenterImpl
    }


    inner class NewsListPresenterImpl : NewsListPresenter {
        private lateinit var clickSubjects: ArrayList<PublishSubject<NewsItemView>>
        private lateinit var listItems: ArrayList<NewsItem>
        private fun createPublishSubjects() {
            val elementsToUpdate = getListCount() - clickSubjects.size
            for (i in 0 until elementsToUpdate) {
                clickSubjects.add(PublishSubject.create<NewsItemView>())
            }
        }


        override fun getClickSubject(position: Int): Observer<NewsItemView> {
            return clickSubjects.get(position)
        }

        override fun getListCount(): Int {
            return listItems.size
        }

        override fun bindView(newsItemView: NewsItemView) {
            var position: Int = newsItemView.pos
            disposables.add(
                clickSubjects.get(position).subscribe({ clickSubjectsNewsItemView -> router.navigateTo(Screens.Companion.NewsScreen()) },
                    { Timber.e(it) })
            )
        }

        override fun setNewsItemsList(newsItems: ArrayList<NewsItem>) {
            this.listItems = newsItems
            createPublishSubjects()
        }
    }
}