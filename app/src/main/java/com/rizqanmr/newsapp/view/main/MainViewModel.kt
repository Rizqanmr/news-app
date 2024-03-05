package com.rizqanmr.newsapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.newsapp.datasources.NewsPagingSource
import com.rizqanmr.newsapp.network.model.ArticlesItem
import com.rizqanmr.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNews(): LiveData<PagingData<ArticlesItem>> {
        return Pager(PagingConfig(pageSize = 10)) {
            NewsPagingSource(newsRepository, "us")
        }.liveData.cachedIn(viewModelScope)
    }
}