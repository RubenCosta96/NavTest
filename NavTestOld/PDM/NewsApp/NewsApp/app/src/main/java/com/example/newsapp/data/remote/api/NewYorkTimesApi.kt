package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.model.ApiArticleDetailsResponseDto
import com.example.newsapp.data.remote.model.ApiArticlesResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {
    val api: NewYorkTimesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewYorkTimesApi::class.java)
    }
}


interface NewYorkTimesApi {
    @GET("topstories/v2/arts.json")
    suspend fun getArticls(
        @Query("api-key") apiKey: String = "6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3"
    ): ApiArticlesResponseDto

    @GET("search/v2/articlesearch.json?")
    suspend fun getArticleDetails(
        @Query("fq") fq: String,
        @Query("api-key") apiKey: String = "6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3"
    ): ApiArticleDetailsResponseDto
}