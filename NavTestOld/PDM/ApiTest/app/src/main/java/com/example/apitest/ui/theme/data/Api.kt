package com.example.apitest.ui.theme.data

import com.example.apitest.ui.theme.data.model.Product
import com.example.apitest.ui.theme.data.model.Products
import com.example.apitest.ui.theme.data.model.Review
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface Api {
//
//    @GET("products/search?q=phone")
//    suspend fun getProductsList(): Products
//
//    companion object{
//        const val BASE_URL = "https://dummyjson.com/"
//    }
//}
interface Api{
    @GET("products/search")
    suspend fun getProductsList(
        @Query("q") query: String
    ): Products

    @GET("products/{id}/reviews")
    suspend fun getProductReviews(
        @Path("id") id: Int
    ): List<Review>

    companion object{
        const val BASE_URL = "https://dummyjson.com/"
    }

}

