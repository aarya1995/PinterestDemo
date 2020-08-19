package com.example.pinterestdemo.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

object UUIDJsonAdapter {
    @FromJson
    fun fromJson(json: String?): UUID? = json?.let(UUID::fromString)

    @ToJson
    fun toJson(uuid: UUID?): String? = uuid?.toString()
}
