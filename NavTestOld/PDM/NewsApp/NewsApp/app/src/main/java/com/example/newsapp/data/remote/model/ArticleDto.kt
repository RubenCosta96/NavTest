package com.example.newsapp.data.remote.model

import com.example.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName


data class ArticleDto (
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("subsection")
    val subsection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
){
    fun toArticle(): Article {
        return Article(abstract = abstract, section = section, subsection = subsection, title = title, url = url)
    }
}

