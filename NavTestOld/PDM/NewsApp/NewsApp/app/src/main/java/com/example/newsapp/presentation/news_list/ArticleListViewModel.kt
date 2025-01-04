package com.example.newsapp.presentation.news_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.api.RetrofitInstance
import com.example.newsapp.data.repository.ArticlesRepositoryImppll
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.use_case.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel: ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = ArticlesRepositoryImppll(api)
    private val getArticlesUseCase = GetArticlesUseCase(repository)

    val articles = MutableStateFlow<List<Article>>(emptyList())

    fun fetchArticles(){
        viewModelScope.launch {
            try {
                articles.value = getArticlesUseCase()
            }catch (e: Exception){
                articles.value = emptyList()
                Log.e("CoinListViewModel", "Erro ao pedir detalhes: ${e.message}")
            }
        }
    }
}