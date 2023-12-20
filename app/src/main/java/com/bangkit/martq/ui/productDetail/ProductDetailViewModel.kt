package com.bangkit.martq.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class ProductDetailViewModel(private val productRepo: ProductRepository, private val cartRepo: CartRepository) : ViewModel() {

    fun getProduct(id: Int) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = productRepo.getProductById(id)))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }

    fun addToCart(cart: Cart) {
        cartRepo.insert(cart)
    }

}