package com.example.apitest.ui.theme.data

import com.example.apitest.ui.theme.data.model.Product
import com.example.apitest.ui.theme.data.model.Review
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList(): Flow<Result<List<Product>>>
    suspend fun getProductsReviews(id: Int): Flow<Result<List<Review>>>
}