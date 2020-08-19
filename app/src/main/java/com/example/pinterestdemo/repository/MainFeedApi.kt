package com.example.pinterestdemo.repository

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MainFeedApi {

    @GET("api/pinData{page}.json")
    fun getFeed(@Path("page") page: String): Single<FeedPage>
}
