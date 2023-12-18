package com.bangkit.martq.data.remote.api

import com.bangkit.martq.data.remote.response.AllCategoriesResponse
import com.bangkit.martq.data.remote.response.AllOrdersResponse
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.data.remote.response.PostOrderResponse
import com.bangkit.martq.data.remote.response.ProductDetailResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("pesanan")
    suspend fun getOrders(): AllOrdersResponse

    @FormUrlEncoded
    @POST("pesanan")
    suspend fun postOrder(
        @Field("id_user") idUser: Int,
        @Field("is_delivery") isDelivery: String,
        @Field("id_rekening") idRekening: String?,
        @Field("id_market") idMarket: Int,
        @Field("biaya_ongkos_kirim") biayaOngkosKirim: Int,
        @Field("total_harga") totalHarga: Int,
        @Field("produk") products: List<Int>,
    ): PostOrderResponse
}