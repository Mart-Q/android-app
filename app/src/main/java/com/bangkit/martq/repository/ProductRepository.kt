package com.bangkit.martq.repository

import com.bangkit.martq.data.remote.api.ApiService

class ProductRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getProducts() = apiService.getProducts()

    suspend fun getProductById(id: Int) = apiService.getProductById(id)

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(apiService: ApiService): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(apiService)
            }.also { instance = it }
    }
}