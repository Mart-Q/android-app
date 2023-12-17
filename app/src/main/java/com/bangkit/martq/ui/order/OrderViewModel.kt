package com.bangkit.martq.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.martq.data.local.datastore.ProfilePreferences
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.repository.CartRepository

class OrderViewModel(private val cartRepo: CartRepository, private val profilePref: ProfilePreferences) : ViewModel() {

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

    fun getAddress(): LiveData<String> {
        return profilePref.getProfileAddress().asLiveData()
    }

    fun getPhone(): LiveData<String> {
        return profilePref.getProfilePhone().asLiveData()
    }

}