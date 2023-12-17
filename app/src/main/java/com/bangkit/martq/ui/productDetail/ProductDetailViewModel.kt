package com.bangkit.martq.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.remote.response.ProductDetailResponse
import com.bangkit.martq.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRepo: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<ProductDetailResponse>()
    val product: LiveData<ProductDetailResponse> get() = _product

    fun getProduct(id: Int) {
        viewModelScope.launch {
            try {
                _product.value = productRepo.getProductById(id)
            } catch (e: Exception) {

            }
        }
    }
}