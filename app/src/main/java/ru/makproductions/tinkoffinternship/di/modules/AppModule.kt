package ru.makproductions.tinkoffinternship.di.modules

import dagger.Module
import dagger.Provides
import ru.makproductions.tinkoffinternship.App

@Module
class AppModule(private var app: App) {
    @Provides
    fun getApp(): App {
        return app
    }
}