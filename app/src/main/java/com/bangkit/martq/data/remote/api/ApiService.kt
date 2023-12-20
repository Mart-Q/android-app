package com.bangkit.martq.data.remote.api

import com.bangkit.martq.data.remote.response.AllCategoriesResponse
import com.bangkit.martq.data.remote.response.AllOrdersResponse
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.data.remote.response.LoginResponse
import com.bangkit.martq.data.remote.response.PostOrderResponse
import com.bangkit.martq.data.remote.response.ProductDetailResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

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

    @GET("pesanan/{id}")
    suspend fun getOrders(
        @Path("id") id: Int
    ): AllOrdersResponse

    @FormUrlEncoded
    @POST("pesanan")
    suspend fun postOrder(
        @Field("id_user") idUser: Int,
        @Field("is_delivery") isDelivery: String,
        @Field("id_rekening") idRekening: Int?,
        @Field("id_market") idMarket: Int,
        @Field("biaya_ongkos_kirim") biayaOngkosKirim: Int,
        @Field("total_harga") totalHarga: Int,
        @Field("status") status: String = "Menghubungi pihak pasar",
        @Field("produk") products: List<String>,
    ): PostOrderResponse
}