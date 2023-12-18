package com.bangkit.martq.ui.productPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class ProductsViewModel(private val productRepo: ProductRepository) : ViewModel() {

    fun getProductsByCategory(category: String) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = productRepo.getProductsByCategory(category)))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }
}