package com.app.calllogs.di.repository

import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.network.ApiResult


interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult<LoginResponse>
}