package com.bangkit.martq.ui.recommencation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.martq.repository.RecipeRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers

class RecommendationViewModel(private val recipeRepo: RecipeRepository) : ViewModel() {

    fun getFoodInspiration() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = recipeRepo.getFoodInspiration()))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }
}