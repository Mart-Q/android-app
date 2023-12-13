package com.bangkit.martq.data.remote.api

import com.bangkit.martq.data.remote.response.AllCategoriesResponse
import com.bangkit.martq.data.remote.response.AllProductsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("produk")
    suspend fun getProducts(): AllProductsResponse

    @GET("kategori")
    suspend fun getCategories(): AllCategoriesResponse
}