package com.bangkit.martq.repository

import com.bangkit.martq.data.remote.api.ApiService

class RecipeRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getFoodInspiration() = apiService.getFoodInspiration()

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null
        fun getInstance(apiService: ApiService): RecipeRepository =
            instance ?: synchronized(this) {
                instance ?: RecipeRepository(apiService)
            }.also { instance = it }
    }
}