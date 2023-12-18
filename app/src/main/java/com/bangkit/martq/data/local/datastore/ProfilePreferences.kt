package com.bangkit.martq.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfilePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val PROFILE_NAME = stringPreferencesKey("profile_name")
    private val PROFILE_EMAIL = stringPreferencesKey("profile_email")
    private val PROFILE_PHONE = stringPreferencesKey("profile_phone")
    private val PROFILE_ADDRESS = stringPreferencesKey("profile_address")

    fun getProfileName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_NAME] ?: ""
        }
    }

    suspend fun saveProfileName(name: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_NAME] = name
        }
    }

    fun getProfileEmail(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_EMAIL] ?: ""
        }
    }

    suspend fun saveProfileEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_EMAIL] = email
        }
    }

    fun getProfilePhone(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_PHONE] ?: ""
        }
    }

    suspend fun saveProfilePhone(phone: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_PHONE] = phone
        }
    }

    fun getProfileAddress(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_ADDRESS] ?: ""
        }
    }

    suspend fun saveProfileAddress(address: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_ADDRESS] = address
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProfilePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ProfilePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ProfilePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}