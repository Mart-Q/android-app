package com.bangkit.martq.repository

import com.bangkit.martq.data.remote.api.ApiService

class OrderRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getOrders(
        idUser: Int
    ) = apiService.getOrders(
        idUser
    )

    suspend fun postOrder(
        idUser: Int,
        isDelivery: String,
        idRekening: Int?,
        idMarket: Int,
        biayaOngkosKirim: Int,
        totalHarga: Int,
        status: String,
        products: String,
    ) = apiService.postOrder(
        idUser,
        isDelivery,
        idRekening,
        idMarket,
        biayaOngkosKirim,
        totalHarga,
        status,
        products
    )

    companion object {
        @Volatile
        private var instance: OrderRepository? = null
        fun getInstance(apiService: ApiService): OrderRepository =
            instance ?: synchronized(this) {
                instance ?: OrderRepository(apiService)
            }.also { instance = it }
    }
}