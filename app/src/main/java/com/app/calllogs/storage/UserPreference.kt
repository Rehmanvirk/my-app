package com.app.calllogs.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.app.calllogs.di.data.LoginResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        const val SHARED_PREF_NAME = "user_credential_Prefer"
        const val USER_DATA = "user_login_data"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUserPreference(loginResponse: LoginResponse) {
        sharedPreferences.edit {
            putString(USER_DATA, gson.toJson(loginResponse))
        }
    }

    fun getUserPreference(): LoginResponse? {
        val json = sharedPreferences.getString(USER_DATA, null)
        return try {
            gson.fromJson(json, LoginResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun clearStorage() = sharedPreferences.edit { clear() }
}