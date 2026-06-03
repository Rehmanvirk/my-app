package com.app.calllogs.di.repository

import android.R.attr.password
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealDetailResponse
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.network.ApiResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DealsRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : DealsRepository {

    override suspend fun getDeals(): ApiResult<DealResponse>  {
        return try {
            val res = api.getDeals()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getDealDetail(id : String): ApiResult<DealDetailResponse> {
        return try {
            val res = api.getDealDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }
}