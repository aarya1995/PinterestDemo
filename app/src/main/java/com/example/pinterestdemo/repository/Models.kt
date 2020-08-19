package com.example.pinterestdemo.repository

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class FeedPage(
    val stories: List<Pin>,
    val nextPageId: String?
)

@JsonClass(generateAdapter = true)
data class Pin(
    val id: UUID,
    val body: String,
    val image: Image,
    val author: String
)

@JsonClass(generateAdapter = true)
data class Image(
    val url: String,
    val width: Int,
    val height: Int
)
