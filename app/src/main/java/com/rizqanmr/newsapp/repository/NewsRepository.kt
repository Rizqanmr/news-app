package com.rizqanmr.newsapp.repository

import com.rizqanmr.newsapp.datasources.RemoteDataSource
import javax.inject.Inject

class NewsRepository  @Inject constructor(private val remoteDataSource: RemoteDataSource) {
}