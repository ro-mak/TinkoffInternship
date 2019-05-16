package ru.makproductions.tinkoffinternship.di

import dagger.Component
import ru.makproductions.tinkoffinternship.di.modules.AppModule
import ru.makproductions.tinkoffinternship.di.modules.CacheModule
import ru.makproductions.tinkoffinternship.di.modules.NetworkModule
import ru.makproductions.tinkoffinternship.di.modules.RepoModule
import ru.makproductions.tinkoffinternship.ui.activity.MainActivity

@Component(modules = [AppModule::class, RepoModule::class, CacheModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}