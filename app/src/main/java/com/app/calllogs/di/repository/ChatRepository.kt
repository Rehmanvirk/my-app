package com.app.calllogs.di.repository

import com.app.calllogs.di.data.Chat
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.MessagesResponse
import com.app.calllogs.di.data.SendMessageResponse
import com.app.calllogs.di.network.ApiResult

interface ChatRepository {
    suspend fun getConversations(): ApiResult<Chat>

    suspend fun getMessagesList(id : String): ApiResult<MessagesResponse>

    suspend fun sendMessage(id : String,text : String): ApiResult<SendMessageResponse>


}