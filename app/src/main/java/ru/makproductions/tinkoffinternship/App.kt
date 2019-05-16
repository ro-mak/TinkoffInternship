package ru.makproductions.tinkoffinternship

import android.app.Application
import ru.makproductions.tinkoffinternship.di.AppComponent
import ru.makproductions.tinkoffinternship.di.DaggerAppComponent
import ru.makproductions.tinkoffinternship.di.modules.AppModule
import ru.makproductions.tinkoffinternship.model.db.NewsDatabase
import timber.log.Timber

class App : Application() {
    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
        NewsDatabase.create(this)
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}