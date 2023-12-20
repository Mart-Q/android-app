package com.bangkit.martq.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.repository.AuthRepository
import com.bangkit.martq.repository.ProfileRepository
import com.bangkit.martq.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val profileRepo: ProfileRepository,
    private val authRepo: AuthRepository,
): ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            profileRepo.saveSession(user)
        }
    }

    fun login(
        email: String,
        password: String
    ) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            emit(
                ResultState.Success(data = authRepo.login(email, password)))
        } catch (e: Exception) {
            emit(ResultState.Error(error = e.message ?: "Error Occurred!"))
        }
    }
}