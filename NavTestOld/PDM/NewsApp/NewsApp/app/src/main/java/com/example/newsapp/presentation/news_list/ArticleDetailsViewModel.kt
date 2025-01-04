package com.example.newsapp.presentation.news_list


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.api.RetrofitInstance
import com.example.newsapp.data.repository.ArticlesRepositoryImppll
import com.example.newsapp.domain.model.ArticleDetail
import com.example.newsapp.domain.use_case.GetArticleDetailsUseCase
import com.example.newsapp.domain.use_case.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ArticleDetailsViewModel: ViewModel(){
    private val api = RetrofitInstance.api
    private val repository = ArticlesRepositoryImppll(api)
    private val getArticleDetailsUseCase = GetArticleDetailsUseCase(repository)

    val articleDetails = MutableStateFlow<ArticleDetail?>(null)

    fun fetchArticleDetails(webUrl: String){
        viewModelScope.launch {
            try {
                articleDetails.value = getArticleDetailsUseCase(webUrl)
            }catch (e: Exception){
                Log.e("CoinListViewModel", "Erro ao pedir detalhes: ${e.message}")
                articleDetails.value = null
            }
        }
    }
}