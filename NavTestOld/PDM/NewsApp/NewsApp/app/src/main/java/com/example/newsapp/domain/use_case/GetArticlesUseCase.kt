package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.ArticleRepository

class GetArticlesUseCase(private val repository: ArticleRepository){
    suspend operator fun invoke():List<Article>
    {
        return repository.getArticles()
    }
}