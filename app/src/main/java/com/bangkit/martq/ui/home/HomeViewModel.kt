package com.bangkit.martq.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val productRepo: ProductRepository, private val categoryRepo: ProductCategoryRepository) : ViewModel() {

    fun getProducts() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = productRepo.getProducts()))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = categoryRepo.getCategories()))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }
}