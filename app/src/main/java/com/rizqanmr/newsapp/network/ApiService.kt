package com.rizqanmr.newsapp.network

import com.rizqanmr.newsapp.network.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int
    ) : NewsModel
}