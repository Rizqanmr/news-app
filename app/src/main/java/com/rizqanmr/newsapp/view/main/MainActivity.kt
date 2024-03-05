package com.rizqanmr.newsapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqanmr.newsapp.databinding.ActivityMainBinding
import com.rizqanmr.newsapp.databinding.ItemNewsBinding
import com.rizqanmr.newsapp.network.model.ArticlesItem
import com.rizqanmr.newsapp.utils.Constant
import com.rizqanmr.newsapp.view.adapter.LoadingStateAdapter
import com.rizqanmr.newsapp.view.adapter.NewsAdapter
import com.rizqanmr.newsapp.view.newsdetail.NewsDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupViewPage()
    }

    private fun setupObservers() {
        viewModel.getNews().observe(this) {
            newsAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewPage() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = newsAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { newsAdapter.retry() }
            )
        }

        newsAdapter.setNewsListener(object : NewsAdapter.NewsListener {
            override fun onItemClick(itemNewsBinding: ItemNewsBinding, item: ArticlesItem?) {
                val optionCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        Pair(itemNewsBinding.ivNews, "image")
                    )

                NewsDetailActivity.newIntent(this@MainActivity, optionCompat.toBundle()
                    ?.apply { putParcelable(Constant.EXTRA_NEWS, item) })
            }
        })
    }
}