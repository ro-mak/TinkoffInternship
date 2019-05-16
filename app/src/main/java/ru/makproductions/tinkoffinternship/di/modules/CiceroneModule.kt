package ru.makproductions.tinkoffinternship.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class CiceroneModule {
    private val cicerone = Cicerone.create()

    @Provides
    fun cicerone(): Cicerone<Router> {
        return cicerone
    }

    @Provides
    fun navigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    @Provides
    fun router(): Router {
        return cicerone.router
    }
}