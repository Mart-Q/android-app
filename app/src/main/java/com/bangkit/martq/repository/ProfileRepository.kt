package com.bangkit.martq.repository

import com.bangkit.martq.data.local.datastore.ProfilePreferences
import com.bangkit.martq.data.local.datastore.UserModel
import kotlinx.coroutines.flow.Flow

class ProfileRepository private constructor(
    private val profilePreference: ProfilePreferences
) {
    suspend fun saveSession(user: UserModel) {
        profilePreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return profilePreference.getSession()
    }

    suspend fun logout() {
        profilePreference.logout()
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(
            profilePreference: ProfilePreferences
        ): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(profilePreference)
            }.also { instance = it }
    }
}