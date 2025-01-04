package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleDetail
import retrofit2.http.Url

interface ArticleRepository {
    suspend fun getArticles(): List<Article>
    suspend fun getArticleDetail(webUrl: String): ArticleDetail
}