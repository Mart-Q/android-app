package com.bangkit.martq.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.repository.ProductRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val productRepo: ProductRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _products = MutableLiveData<AllProductsResponse>()
    val products: LiveData<AllProductsResponse> get() = _products

    init {
        getProducts()
    }

    fun getProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val stories = productRepo.getProducts()
                _products.value = stories
            } catch (e: Exception) {

            }
            _isLoading.value = false
        }

    }
}