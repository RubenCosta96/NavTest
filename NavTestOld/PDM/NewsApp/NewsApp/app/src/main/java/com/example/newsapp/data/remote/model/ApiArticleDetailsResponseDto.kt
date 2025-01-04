package com.example.newsapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class ApiArticleDetailsResponseDto(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: String
)