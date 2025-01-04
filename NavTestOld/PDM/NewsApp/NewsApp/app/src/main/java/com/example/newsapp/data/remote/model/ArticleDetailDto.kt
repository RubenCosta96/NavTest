package com.example.newsapp.data.remote.model

import com.example.newsapp.domain.model.ArticleDetail
import com.google.gson.annotations.SerializedName

data class ArticleDetailDto (
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("lead_paragraph")
    val leadParagraph: String,
    @SerializedName("pub_date")
    val pubDate: String,
    @SerializedName("section_name")
    val sectionName: String,
    @SerializedName("snippet")
    val snippet: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("type_of_material")
    val typeOfMaterial: String,
    @SerializedName("web_url")
    val webUrl: String,
){
    fun toArticleDetail(): ArticleDetail {
        return ArticleDetail(abstract = abstract, leadParagraph = leadParagraph, pubDate = pubDate,
            sectionName = sectionName, snippet = snippet, source = source, typeOfMaterial = typeOfMaterial,
            webUrl = webUrl)
        //Implementar depois
    }
}