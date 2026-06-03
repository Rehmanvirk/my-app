package com.app.calllogs.di.repository

import android.R.attr.password
import com.app.calllogs.di.data.ContactDetailsResponse
import com.app.calllogs.di.data.ContactResponse
import com.app.calllogs.di.data.CreateContactReq
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.network.ApiResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : ContactsRepository {

    override suspend fun getContacts(id: String): ApiResult<ContactResponse>  {
        return try {
            val res = api.getContacts(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun createContact(req: CreateContactReq): ApiResult<ContactResponse> {
        return try {
            val res = api.createContact(req)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getContactDetails(id: String): ApiResult<ContactDetailsResponse>  {
        return try {
            val res = api.getContactDetails(id)
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