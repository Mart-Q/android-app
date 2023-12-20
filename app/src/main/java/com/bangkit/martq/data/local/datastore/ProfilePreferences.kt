package com.bangkit.martq.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class ProfilePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[PROFILE_EMAIL] ?: "",
                preferences[PROFILE_NAME] ?: "",
                preferences[PROFILE_PHONE] ?: "",
                preferences[PROFILE_ADDRESS] ?: ""
            )
        }
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[PROFILE_EMAIL] = user.email
            preferences[PROFILE_NAME] = user.name
            preferences[PROFILE_PHONE] = user.phone
            preferences[PROFILE_ADDRESS] = user.address
        }
    }

    suspend fun savePhone(phone: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_PHONE] = phone
        }
    }

    suspend fun saveAddress(address: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_ADDRESS] = address
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProfilePreferences? = null

        private val PROFILE_NAME = stringPreferencesKey("profile_name")
        private val PROFILE_EMAIL = stringPreferencesKey("profile_email")
        private val PROFILE_PHONE = stringPreferencesKey("profile_phone")
        private val PROFILE_ADDRESS = stringPreferencesKey("profile_address")

        fun getInstance(dataStore: DataStore<Preferences>): ProfilePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ProfilePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}