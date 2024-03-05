package com.rizqanmr.newsapp.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rizqanmr.newsapp.network.model.ArticlesItem
import com.rizqanmr.newsapp.repository.NewsRepository

class NewsPagingSource(
    private val newsRepository: NewsRepository,
    private val country: String
) : PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = newsRepository.getTopHeadlines(country, nextPageNumber)

            LoadResult.Page(
                data = response?.articles.orEmpty(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response?.articles?.isEmpty() == true) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}