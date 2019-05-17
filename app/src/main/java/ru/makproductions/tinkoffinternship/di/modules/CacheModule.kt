package ru.makproductions.tinkoffinternship.di.modules

import dagger.Module
import dagger.Provides
import ru.makproductions.tinkoffinternship.model.cache.ICache
import ru.makproductions.tinkoffinternship.model.room.RoomCache

@Module
class CacheModule {
    @Provides
    fun getCache(): ICache {
        return RoomCache()
    }
}