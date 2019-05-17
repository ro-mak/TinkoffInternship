package ru.makproductions.tinkoffinternship.presenter

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import ru.makproductions.tinkoffinternship.view.NewsView

class NewsPresenter(scheduler: Scheduler) : MvpPresenter<NewsView>()