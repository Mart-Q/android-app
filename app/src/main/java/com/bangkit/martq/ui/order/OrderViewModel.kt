package com.bangkit.martq.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.bangkit.martq.data.local.datastore.ProfilePreferences
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.OrderRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class OrderViewModel(
    private val cartRepo: CartRepository,
    private val profilePref: ProfilePreferences,
    private val orderRepo: OrderRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Cart>>()
    val products: LiveData<List<Cart>> get() = _products

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    init {
        getProducts()
    }

    fun getProducts() {
        cartRepo.getAllCartItems().observeForever {
            _products.value = it
        }
    }

    fun getUserCurrentEmail(): String {
        return profilePref.getProfileEmail().asLiveData().value.toString()
    }

    fun getCurrentUserName(): String {
        return profilePref.getProfileName().asLiveData().value.toString()
    }

    fun getUserEmail() {
        _userEmail.value = profilePref.getProfileEmail().asLiveData().value.toString()
    }

    fun getUserAddress(): LiveData<String> {
        return profilePref.getProfileAddress().asLiveData()
    }

    fun getUserPhone(): LiveData<String> {
        return profilePref.getProfilePhone().asLiveData()
    }

    fun makeOrder(
        idUser: Int,
        isDelivery: String,
        idRekening: String?,
        idMarket: Int,
        biayaOngkosKirim: Int,
        totalHarga: Int,
        products: List<Int>,
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

}