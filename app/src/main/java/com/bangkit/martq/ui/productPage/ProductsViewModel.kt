package com.bangkit.martq.ui.productPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductsViewModel(private val productRepo: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<AllProductsResponse>()
    val products: LiveData<AllProductsResponse> get() = _products

    fun getProductsByCategory(category: String) {
        try {
            viewModelScope.launch {
                _products.value = productRepo.getProductsByCategory(category)
            }
        } catch (e: Exception) {

        }
    }
}