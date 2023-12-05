package com.bangkit.martq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.remote.response.AllCategoriesResponse
import com.bangkit.martq.data.remote.response.AllProductsResponse
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepo: ProductRepository, private val categoryRepo: ProductCategoryRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _products = MutableLiveData<AllProductsResponse>()
    val products: LiveData<AllProductsResponse> get() = _products

    private val _categories = MutableLiveData<AllCategoriesResponse>()
    val category: LiveData<AllCategoriesResponse> get() = _categories

    init {
        getProducts()
        getCategories()
    }

    fun getProducts() {
        _isLoading.value = true
        try {
            viewModelScope.launch {
                _products.value = productRepo.getProducts()
            }
        } catch (e: Exception) {

        }
        _isLoading.value = false
    }

    fun getCategories() {
        _isLoading.value = true
        try {
            viewModelScope.launch {
                _categories.value = categoryRepo.getCategories()
            }
        } catch (e: Exception) {

        }
        _isLoading.value = false
    }
}