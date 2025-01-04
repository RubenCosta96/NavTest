package com.example.apitest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.ui.theme.data.ProductsRepository
import com.example.apitest.ui.theme.data.Result
import com.example.apitest.ui.theme.data.model.Review
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReviewsViewModel (
    private val productsRepository: ProductsRepository
): ViewModel(){

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    fun getProductReviews(productId: Int) {
        viewModelScope.launch {
            productsRepository.getProductsReviews(productId).collect{ result ->
                    when(result){
                        is Result.Error -> {
                            _showErrorToastChannel.send(true)
                        }
                        is Result.Success -> {
                            result.data?.let { reviewsList ->
                                _reviews.update { reviewsList }
                            }
                        }
                    }
            }
        }
    }
}