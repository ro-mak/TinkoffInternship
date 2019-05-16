package ru.makproductions.tinkoffinternship.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import ru.makproductions.tinkoffinternship.view.main.MainView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter(scheduler: Scheduler) : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

}