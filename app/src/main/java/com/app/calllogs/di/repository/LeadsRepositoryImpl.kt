package com.app.calllogs.di.repository

import android.R.attr.password
import com.app.calllogs.di.data.ContactResponse
import com.app.calllogs.di.data.ConvertLeadToContact
import com.app.calllogs.di.data.ConvertLeadToDeal
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.EditTemplateResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.TemplateResponse
import com.app.calllogs.di.network.ApiResult
import kotlinx.serialization.json.JsonObject

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LeadsRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : LeadsRepository {

    override suspend fun getLeads(id: String): ApiResult<LeadResponse>  {
        return try {
            val res = api.getLeads(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getLeadsTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getLeadTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getContactTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getContactTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createLead(req: CreateLeadRequest): ApiResult<LeadResponse> {
        return try {
            val res = api.createLead(req)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createLead(jsonObject: com.google.gson.JsonObject): ApiResult<LeadResponse> {
        return try {
            val res = api.createLead1(jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createContact(jsonObject: com.google.gson.JsonObject): ApiResult<ContactResponse> {
        return try {
            val res = api.createContact1(jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }


    override suspend fun getLeadDetails(id: String): ApiResult<LeadDetailsResponse>  {
        return try {
            val res = api.getLeadDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun convertLeadToContact(id : String,req: ConvertLeadToContact): ApiResult<LeadResponse> {
        return try {
            val res = api.convertLeadToContact(id,req)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun convertLeadToDeal(id : String,req: ConvertLeadToDeal): ApiResult<LeadResponse> {
        return try {
            val res = api.convertLeadToDeal(id,req)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getDealTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getDealTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createDeal(jsonObject: com.google.gson.JsonObject): ApiResult<LeadResponse> {
        return try {
            val res = api.createDeal(jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getTaskTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getTaskTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createTask(jsonObject: com.google.gson.JsonObject): ApiResult<LeadResponse> {
        return try {

            val res = api.createTask(jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getMeetingTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getMeetingTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createMeeting(jsonObject: com.google.gson.JsonObject): ApiResult<LeadResponse> {
        return try {
            val res = api.createMeeting(jsonObject)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getCallTemplate(): ApiResult<TemplateResponse>  {
        return try {
            val res = api.getCallTemplate()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getEditContactTemplate(id: String): ApiResult<EditTemplateResponse> {
        return try {
            val res = api.getContactsDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getEditLeadTemplate(id: String): ApiResult<EditTemplateResponse> {
        return try {
            val res = api.getLeadsDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getEditDealTemplate(id: String): ApiResult<EditTemplateResponse> {
        return try {
            val res = api.getEditDealDetails(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createCall(jsonObject: com.google.gson.JsonObject): ApiResult<LeadResponse> {
        return try {
            val res = api.createCall(jsonObject)
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
