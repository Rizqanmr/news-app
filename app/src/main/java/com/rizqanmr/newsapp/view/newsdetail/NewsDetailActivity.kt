package com.rizqanmr.newsapp.view.newsdetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.rizqanmr.newsapp.R
import com.rizqanmr.newsapp.databinding.ActivityNewsDetailBinding
import com.rizqanmr.newsapp.network.model.ArticlesItem
import com.rizqanmr.newsapp.utils.Constant
import com.rizqanmr.newsapp.utils.setFitImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity, bundle: Bundle?) {
            activity.startActivity(
                Intent(activity, NewsDetailActivity::class.java).apply {
                    bundle?.let { putExtras(it) }
                }
            )
        }
    }

    private lateinit var binding: ActivityNewsDetailBinding
    private var news: ArticlesItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        news = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_NEWS, ArticlesItem::class.java)
        } else {
            intent.getParcelableExtra(Constant.EXTRA_NEWS)
        }

        setupViewPage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPage() {
        with(binding) {
            setSupportActionBar(toolbarNewsDetail)
            supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)

                val upArrow = ContextCompat.getDrawable(this@NewsDetailActivity, R.drawable.ic_arrow_back)
                upArrow?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(upArrow)
            }

            tvTitle.text = news?.title
            tvSource.text = news?.source?.name
            tvPublishedAt.text = news?.getContentDate()
            tvAuthor.text = news?.getAuthorName()
            tvDescription.text = if (news?.description.isNullOrEmpty()) Constant.NO_DESCRIPTION else news?.description
            tvContent.text = if (news?.content.isNullOrEmpty()) Constant.NO_CONTENT else HtmlCompat.fromHtml(news?.content.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            ivNews.setFitImageUrl(news?.urlToImage, R.drawable.ic_error)
        }
    }
}