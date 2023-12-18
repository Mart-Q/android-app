package com.bangkit.martq.di

import android.app.Application
import android.content.Context
import com.bangkit.martq.data.local.datastore.ProfilePreferences
import com.bangkit.martq.data.local.datastore.dataStore
import com.bangkit.martq.data.remote.api.ApiConfig
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.OrderRepository
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

    fun provideCartRepository(application: Application): CartRepository {
        return CartRepository(application)
    }

    fun provideProfilePreferences(application: Application): ProfilePreferences {
        return ProfilePreferences.getInstance(application.dataStore)
    }

    fun provideOrderRepository(context: Context): OrderRepository {
        val apiService = ApiConfig.getApiService()
        return OrderRepository.getInstance(apiService)
    }
}