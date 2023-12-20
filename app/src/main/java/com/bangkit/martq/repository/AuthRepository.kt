package com.bangkit.martq.repository

import com.bangkit.martq.data.remote.api.ApiService

class AuthRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String) = apiService.login(
            email,
            password
    )

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(apiService: ApiService): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}