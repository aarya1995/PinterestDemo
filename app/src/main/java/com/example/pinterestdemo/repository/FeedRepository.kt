package com.example.pinterestdemo.repository

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(
    private val mainFeedApi: MainFeedApi
) {
    fun fetchFeed(page: String): Single<FeedPage> {
        return mainFeedApi.getFeed(page)
    }
}
