package com.bangkit.martq.di

import android.content.Context
import com.bangkit.martq.data.remote.api.ApiConfig
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository

object Injection {
    fun provideProductRepository(context: Context): ProductRepository {
        val apiService = ApiConfig.getApiService()
        return ProductRepository.getInstance(apiService)
    }

    fun provideProductCategoryRepository(context: Context): ProductCategoryRepository {
        val apiService = ApiConfig.getApiService()
        return ProductCategoryRepository.getInstance(apiService)
    }
}