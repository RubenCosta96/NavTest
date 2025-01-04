package com.example.newsapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("docs")
    val docs: List<ArticleDetailDto>,
)