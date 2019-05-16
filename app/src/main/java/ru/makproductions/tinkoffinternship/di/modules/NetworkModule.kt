package ru.makproductions.tinkoffinternship.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.makproductions.tinkoffinternship.model.network.INetApi
import timber.log.Timber
import javax.inject.Named

@Module
class NetworkModule {
    @Named("baseUrl")
    @Provides
    fun getApiBaseUrl(): String {
        return "https://api.tinkoff.ru/"
    }

    @Provides
    fun okHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun getNetService(gson: Gson, @Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient): INetApi {
        Timber.e("getApiService")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create<INetApi>(INetApi::class.java)
    }

    @Provides
    fun getGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}