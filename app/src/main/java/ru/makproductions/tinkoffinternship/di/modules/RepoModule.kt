package ru.makproductions.tinkoffinternship.di.modules

import dagger.Module
import dagger.Provides
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.network.INetApi
import ru.makproductions.tinkoffinternship.model.repo.NewsRepo

@Module
class RepoModule {
    @Provides
    fun getNewsRepo(iNetApi: INetApi, cache: ICache): NewsRepo {
        return NewsRepo(iNetApi, cache)
    }
}