package com.app.calllogs.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_store")

class TokenStore(private val context: Context) {
    private val KEY_ACCESS = stringPreferencesKey("access_token")

    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { it[KEY_ACCESS] }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { it[KEY_ACCESS] = token }
    }

    suspend fun clear() {
        context.dataStore.edit { it.remove(KEY_ACCESS) }
    }
}