package com.rizqanmr.newsapp.datasources

import com.rizqanmr.newsapp.network.ApiService
import com.rizqanmr.newsapp.network.model.NewsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getTopHeadlines(country: String, page: Int) : NewsModel? {
        return withContext(coroutineContext) {
            try {
                val news = apiService.getTopHeadlines(country, page)
                news
            } catch (e: Exception) {
                null
            }
        }
    }
}