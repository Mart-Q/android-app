package com.bangkit.martq.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.OrderRepository
import com.bangkit.martq.repository.ProfileRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class OrderViewModel(
    private val cartRepo: CartRepository,
    private val profileRepo: ProfileRepository,
    private val orderRepo: OrderRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Cart>>()
    val products: LiveData<List<Cart>> get() = _products

    init {
        getProducts()
    }

    fun getProducts() {
        cartRepo.getAllCartItems().observeForever {
            _products.value = it
        }
    }

    fun deleteCart() {
        cartRepo.deleteAll()
    }

    fun makeOrder(
        idUser: Int,
        isDelivery: String,
        idRekening: Int?,
        idMarket: Int,
        biayaOngkosKirim: Int,
        totalHarga: Int,
        products: List<String>,
    ) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(ResultState.Success(data = orderRepo.postOrder(
                idUser,
                isDelivery,
                idRekening,
                idMarket,
                biayaOngkosKirim,
                totalHarga,
                products
            )))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }

    fun getOrderHistory(idUser: Int) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(ResultState.Success(data = orderRepo.getOrders(idUser)))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }

    fun getSession(): LiveData<UserModel> {
        return profileRepo.getSession().asLiveData()
    }
}