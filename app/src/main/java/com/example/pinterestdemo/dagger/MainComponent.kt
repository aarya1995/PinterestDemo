package com.example.pinterestdemo.dagger

import com.example.pinterestdemo.repository.FeedRepository
import com.example.pinterestdemo.repository.MainFeedApi
import com.example.pinterestdemo.util.UUIDJsonAdapter
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    fun feedRepository(): FeedRepository

    companion object {
        val injector by lazy<MainComponent> { DaggerMainComponent.create() }
    }
}

@Module
object MainModule {

    @Singleton @JvmStatic @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(UUIDJsonAdapter)
        .build()

    @Singleton @JvmStatic @Provides
    fun provideRetrofit(moshi: Moshi) = Retrofit.Builder()
        .baseUrl("https://aarya1995.github.io/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton @JvmStatic @Provides
    fun provideMainFeedApi(retrofit: Retrofit): MainFeedApi = retrofit.create(MainFeedApi::class.java)
}
