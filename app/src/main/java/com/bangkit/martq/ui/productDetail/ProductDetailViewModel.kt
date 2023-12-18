package com.bangkit.martq.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.data.remote.response.ProductDetailResponse
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRepo: ProductRepository, private val cartRepo: CartRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _product = MutableLiveData<ProductDetailResponse>()
    val product: LiveData<ProductDetailResponse> get() = _product

    fun getProduct(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _product.value = productRepo.getProductById(id)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(cart: Cart) {
        cartRepo.insert(cart)
    }

}