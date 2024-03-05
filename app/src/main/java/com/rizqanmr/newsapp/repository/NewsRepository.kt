package com.rizqanmr.newsapp.repository

import com.rizqanmr.newsapp.datasources.RemoteDataSource
import com.rizqanmr.newsapp.network.model.NewsModel
import javax.inject.Inject

class NewsRepository  @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getTopHeadlines(country: String, page: Int) : NewsModel? {
        return remoteDataSource.getTopHeadlines(country, page)
    }
}