package com.bangkit.martq.data.remote.api

import com.bangkit.martq.data.remote.response.AllProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): AllProductsResponse
}