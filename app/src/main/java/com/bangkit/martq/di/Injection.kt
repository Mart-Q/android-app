package com.bangkit.martq.di

import android.app.Application
import android.content.Context
import com.bangkit.martq.data.local.datastore.ProfilePreferences
import com.bangkit.martq.data.local.datastore.dataStore
import com.bangkit.martq.data.remote.api.ApiConfig
import com.bangkit.martq.repository.AuthRepository
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.OrderRepository
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.repository.ProfileRepository
import com.bangkit.martq.repository.RecipeRepository

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }

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

    fun provideProfileRepository(context: Context): ProfileRepository {
        val pref = ProfilePreferences.getInstance(context.dataStore)
        return ProfileRepository.getInstance(pref)
    }

    fun provideOrderRepository(context: Context): OrderRepository {
        val apiService = ApiConfig.getApiService()
        return OrderRepository.getInstance(apiService)
    }

    fun provideRecipeRepository(context: Context): RecipeRepository {
        val apiService = ApiConfig.getApiService()
        return RecipeRepository.getInstance(apiService)
    }
}