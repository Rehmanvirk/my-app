package com.app.calllogs.di.repository

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
import com.google.gson.JsonObject
import org.json.JSONObject

interface LeadsRepository {
    suspend fun getLeads(id : String): ApiResult<LeadResponse>

    suspend fun getLeadsTemplate(): ApiResult<TemplateResponse>

    suspend fun getContactTemplate(): ApiResult<TemplateResponse>

    suspend fun createLead(req: CreateLeadRequest):  ApiResult<LeadResponse>

    suspend fun createLead(jsonObject: JsonObject):  ApiResult<LeadResponse>

    suspend fun createContact(jsonObject: JsonObject):  ApiResult<ContactResponse>

    suspend fun getLeadDetails(id : String): ApiResult<LeadDetailsResponse>

    suspend fun convertLeadToContact(id : String,req: ConvertLeadToContact):  ApiResult<LeadResponse>

    suspend fun convertLeadToDeal(id : String,req: ConvertLeadToDeal):  ApiResult<LeadResponse>

    suspend fun createDeal(jsonObject: JsonObject):  ApiResult<LeadResponse>

    suspend fun getDealTemplate(): ApiResult<TemplateResponse>

    suspend fun createTask(jsonObject: JsonObject):  ApiResult<LeadResponse>

    suspend fun getTaskTemplate(): ApiResult<TemplateResponse>

    suspend fun createMeeting(jsonObject: JsonObject):  ApiResult<LeadResponse>

    suspend fun getMeetingTemplate(): ApiResult<TemplateResponse>

    suspend fun createCall(jsonObject: JsonObject):  ApiResult<LeadResponse>

    suspend fun getCallTemplate(): ApiResult<TemplateResponse>

    suspend fun getEditContactTemplate(id : String): ApiResult<EditTemplateResponse>

    suspend fun getEditLeadTemplate(id : String): ApiResult<EditTemplateResponse>

    suspend fun getEditDealTemplate(id : String): ApiResult<EditTemplateResponse>
}