package com.bangkit.martq.repository

import com.bangkit.martq.data.remote.api.ApiService

class ProductCategoryRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getCategories() = apiService.getCategories()

    companion object {
        @Volatile
        private var instance: ProductCategoryRepository? = null
        fun getInstance(apiService: ApiService): ProductCategoryRepository =
            instance ?: synchronized(this) {
                instance ?: ProductCategoryRepository(apiService)
            }.also { instance = it }
    }
}