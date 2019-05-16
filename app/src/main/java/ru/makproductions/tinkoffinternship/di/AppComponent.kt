package ru.makproductions.tinkoffinternship.di

import dagger.Component
import ru.makproductions.tinkoffinternship.di.modules.*
import ru.makproductions.tinkoffinternship.presenter.MainPresenter
import ru.makproductions.tinkoffinternship.ui.activity.MainActivity

@Component(modules = [AppModule::class, RepoModule::class, CacheModule::class, NetworkModule::class, CiceroneModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
}