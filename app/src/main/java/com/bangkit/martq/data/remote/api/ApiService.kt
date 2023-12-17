package com.bangkit.martq.data.remote.api

import com.bangkit.martq.data.remote.response.AllCategoriesResponse
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.data.remote.response.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("produk")
    suspend fun getProducts(): AllProductsResponse

    @GET("kategori")
    suspend fun getCategories(): AllCategoriesResponse

    @GET("produk/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDetailResponse

    @GET("produk")
    suspend fun getProductsByCategory(
        @Query("kategori") category: String
    ): AllProductsResponse
}