package com.app.calllogs.di.repository

import android.R.attr.password
import com.app.calllogs.di.data.Chat
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.data.MessagesResponse
import com.app.calllogs.di.data.SendMessageResponse
import com.app.calllogs.di.network.ApiResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : ChatRepository {

    override suspend fun getConversations(): ApiResult<Chat>  {
        return try {
            val res = api.getConversations()
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun getMessagesList(id : String): ApiResult<MessagesResponse>  {
        return try {
            val res = api.getMessagesList(id)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Error("Login failed: ${e.code()} ${e.response()?.errorBody()?.string()}", e)
        } catch (e: IOException) {
            ApiResult.Error("Network error. Check connection.", e)
        } catch (e: Exception) {
            ApiResult.Error("Something went wrong.", e)
        }
    }

    override suspend fun sendMessage(id : String,text : String): ApiResult<SendMessageResponse>  {
        return try {
            val recId = id.toRequestBody("text/plain".toMediaTypeOrNull())
            val txt = text.toRequestBody("text/plain".toMediaTypeOrNull())


            val res = api.sendMessage(recId,txt)
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