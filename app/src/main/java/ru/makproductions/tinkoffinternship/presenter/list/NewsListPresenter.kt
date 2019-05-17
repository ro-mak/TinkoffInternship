package ru.makproductions.tinkoffinternship.presenter.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.makproductions.tinkoffinternship.model.api.NewsItemLink
import ru.makproductions.tinkoffinternship.model.repo.NewsRepo
import ru.makproductions.tinkoffinternship.navigation.Screens
import ru.makproductions.tinkoffinternship.view.list.NewsItemView
import ru.makproductions.tinkoffinternship.view.list.NewsListView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
            newsItemView.setText(newsItemLink.text + "\n\n" + SimpleDateFormat("dd-MM-yyyy").format(Date(newsItemLink.publicationDate.millis)))
            disposables.add(
                clickSubjects.get(position).subscribe(
                    { clickSubjectsNewsItemView ->
                        Timber.e("Clicked on =" + position)
                        router.navigateTo(Screens.Companion.NewsScreen(newsItemLink.id))
                    },
                    { Timber.e(it) })
            )
        }

        override fun setNewsItemsList(newsItems: ArrayList<NewsItemLink>) {
            Timber.e("setNewsItemsList")
            val observer = object : CompletableObserver {
                override fun onComplete() {
                    createPublishSubjects()
                    viewState.onNewsLoaded()
                }

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            }
            Completable.create({
                val sortedMap = TreeMap<Long, NewsItemLink>()
                try {
                    for (i in 0 until newsItems.size) {
                        val link = newsItems[i]
                        sortedMap.put(link.publicationDate.millis, link)
                    }
                    val sortedList = sortedMap.values.toList() as ArrayList<NewsItemLink>
                    for (i in (sortedList.size - 1) downTo 0) {
                        listItems.add(sortedList[i])
                    }
                    it.onComplete()
                } catch (e: Exception) {
                    it.onError(e)
                }

            }).subscribeOn(Schedulers.computation()).observeOn(scheduler).subscribe(observer)

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