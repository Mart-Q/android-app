package com.bangkit.martq.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.repository.ProfileRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val profileRepo: ProfileRepository): ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            profileRepo.saveSession(user)
        }
    }
}