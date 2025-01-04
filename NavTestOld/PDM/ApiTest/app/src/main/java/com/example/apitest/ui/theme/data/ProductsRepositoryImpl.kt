package com.example.apitest.ui.theme.data

import android.util.Log
import com.example.apitest.ui.theme.data.model.Product
import com.example.apitest.ui.theme.data.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductsRepositoryImpl(
    private val api: Api
): ProductsRepository {
    override suspend fun getProductsList(): Flow<Result<List<Product>>> {
        return flow{
            val productsFromApi = try {
                api.getProductsList(query = "laptop")

            }catch(e: IOException){
                e.printStackTrace()
                emit(Result.Error(message ="Error Loading Products"))
                return@flow
            }catch(e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message ="Error Loading Products"))
                return@flow
            } catch(e: Exception){
                e.printStackTrace()
                emit(Result.Error(message ="Error Loading Products"))
                return@flow
            }
            emit(Result.Success(productsFromApi.products))
        }
    }

    override suspend fun getProductsReviews(id: Int): Flow<Result<List<Review>>> {
        return flow {
            val reviewsFromApi = try {
                api.getProductReviews(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error Loading Reviews"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error Loading Reviews"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error Loading Reviews"))
                return@flow
            }
            emit(Result.Success(reviewsFromApi))
        }
    }

}