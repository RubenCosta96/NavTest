package com.example.newsapp.domain.use_case

import android.util.Log
import com.example.newsapp.domain.model.ArticleDetail
import com.example.newsapp.domain.repository.ArticleRepository

class GetArticleDetailsUseCase(private val repository: ArticleRepository){
    suspend operator fun invoke(articleUrl: String): ArticleDetail {
        //Log.d("CoinListViewModel", "Web URL: ${articleUrl}")
        return repository.getArticleDetail(articleUrl)
    }
}