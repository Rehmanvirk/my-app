package com.app.calllogs.di.repository

import android.R.attr.password
import com.app.calllogs.di.data.ActivitiesResponse
import com.app.calllogs.di.data.CallsDetails
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.data.MeetingsDetails
import com.app.calllogs.di.data.TasksDetails
import com.app.calllogs.di.data.UpdateMeeting
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.ui.home.HomeActivity.Companion.module
import com.google.gson.JsonObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActivitiesRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : ActivitiesRepository {

    override suspend fun getActivities(module : String,zohoId : String): ApiResult<ActivitiesResponse>  {
        return try {
            val res = api.getActivities(module.lowercase(),zohoId)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getMeetingDetails(id: String): ApiResult<MeetingsDetails> {
        return try {
            val res = api.getMeetingDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun updateMeetingDetails(id: String,jsonObject: JsonObject): ApiResult<UpdateMeeting> {
        return try {
            val res = api.updateMeetingDetails(id,jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getCallDetails(id: String): ApiResult<CallsDetails> {
        return try {
            val res = api.getCallsDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun updateCallDetails(
        id: String,
        jsonObject: JsonObject
    ): ApiResult<UpdateMeeting> {
        return try {
            val res = api.updateCallDetails(id,jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getTaskDetails(id: String): ApiResult<TasksDetails> {
        return try {
            val res = api.getTasksDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun updateTaskDetails(
        id: String,
        jsonObject: JsonObject
    ): ApiResult<UpdateMeeting> {
        return try {
            val res = api.updateTaskDetails(id,jsonObject)
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