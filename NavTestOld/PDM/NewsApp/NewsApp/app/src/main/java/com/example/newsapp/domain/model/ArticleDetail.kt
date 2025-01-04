package com.example.newsapp.domain.model

import com.google.gson.annotations.SerializedName

data class ArticleDetail (
    val abstract: String,
    val leadParagraph: String,
    val pubDate: String,
    val sectionName: String,
    val snippet: String,
    val source: String,
    val typeOfMaterial: String,
    val webUrl: String,
)