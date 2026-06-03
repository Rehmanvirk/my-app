package com.app.calllogs.di.repository

import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.network.ApiResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResult<LoginResponse> {
        return try {
            val res = api.login(LoginRequest(email, password))
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string() }", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }
}