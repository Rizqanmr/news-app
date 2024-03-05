package com.rizqanmr.newsapp.network.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rizqanmr.newsapp.utils.Constant
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class ArticlesItem(
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("source") val source: Source,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("content") val content: String?
) : Parcelable {
    @SuppressLint("SimpleDateFormat")
    fun getContentDate(): String? {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(publishedAt.orEmpty())
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            date?.let { dateFormat.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun getAuthorName(): String {
        val authorName = if (author.isNullOrEmpty()) Constant.NO_AUTHOR else author
        return "Author: $authorName"
    }
}

data class NewsModel(
    @SerializedName("totalResults") val totalResults: Int = 0,
    @SerializedName("articles") val articles: List<ArticlesItem>?,
    @SerializedName("status") val status: String = ""
)

@Parcelize
data class Source(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?
) : Parcelable


