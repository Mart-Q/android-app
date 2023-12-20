package com.bangkit.martq.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.repository.ProfileRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val profileRepo: ProfileRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return profileRepo.getSession().asLiveData()
    }

    fun savePhone(phone: String) {
        viewModelScope.launch {
            profileRepo.savePhone(phone)
        }
    }

    fun saveAddress(address: String) {
        viewModelScope.launch {
            profileRepo.saveAddress(address)
        }
    }

    fun logout() {
        viewModelScope.launch {
            profileRepo.logout()
        }
    }
}