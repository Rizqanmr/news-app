package com.rizqanmr.newsapp.datasources

import com.rizqanmr.newsapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}