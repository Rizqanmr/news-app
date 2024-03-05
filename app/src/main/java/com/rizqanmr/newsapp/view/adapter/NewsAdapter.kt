package com.rizqanmr.newsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.newsapp.R
import com.rizqanmr.newsapp.databinding.ItemNewsBinding
import com.rizqanmr.newsapp.network.model.ArticlesItem
import com.rizqanmr.newsapp.utils.setFitImageUrl

class NewsAdapter : PagingDataAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK){

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }

    private lateinit var newsListener: NewsListener

    fun setNewsListener(newsListener: NewsListener) {
        this.newsListener = newsListener
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindData(item, newsListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ArticlesItem?, listener: NewsListener) {
            (binding as? ItemNewsBinding)?.let { itemNews ->
                itemNews.item = item
                with(itemNews) {
                    ivNews.setFitImageUrl(item?.urlToImage, R.drawable.ic_error)
                    cvNews.setOnClickListener { listener.onItemClick(itemNews, item) }
                }
            }
        }
    }

    interface NewsListener {
        fun onItemClick(itemNewsBinding: ItemNewsBinding, item: ArticlesItem?)
    }
}