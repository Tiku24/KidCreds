package com.example.kidcreds.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val key_login = booleanPreferencesKey("login")
class KidCredsPrefDatastore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[key_login] = isLoggedIn
        }
    }

    fun getLoginStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key_login] ?: false
        }
    }
}