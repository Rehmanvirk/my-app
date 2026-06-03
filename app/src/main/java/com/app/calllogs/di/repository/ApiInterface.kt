package com.app.calllogs.di.repository

import com.app.calllogs.di.data.ActivitiesResponse
import com.app.calllogs.di.data.CallsDetails
import com.app.calllogs.di.data.Chat
import com.app.calllogs.di.data.ContactDetailsResponse
import com.app.calllogs.di.data.ContactResponse
import com.app.calllogs.di.data.ConvertLeadToContact
import com.app.calllogs.di.data.ConvertLeadToDeal
import com.app.calllogs.di.data.CreateContactReq
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealDetailResponse
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.EditTemplateResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LoginRequest
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.di.data.MeetingsDetails
import com.app.calllogs.di.data.MessagesResponse
import com.app.calllogs.di.data.SendMessageResponse
import com.app.calllogs.di.data.TasksDetails
import com.app.calllogs.di.data.TemplateResponse
import com.app.calllogs.di.data.UpdateMeeting
import com.app.calllogs.ui.nav.BottomRoute
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {
    @POST("/api/login")
    suspend fun login(@Body req: LoginRequest): LoginResponse

    @GET("/api/leads/{id}")
    suspend fun getLeads(@Path("id") agentId : String): LeadResponse

    @GET("/api/leads/template")
    suspend fun getLeadTemplate(): TemplateResponse

    @POST("/api/leads/create")
    suspend fun createLead(@Body req: CreateLeadRequest): LeadResponse

    @POST("/api/leads/create")
    suspend fun createLead1(@Body jsonObject: JsonObject): LeadResponse

    @GET("/api/leads/get/{id}")
    suspend fun getLeadDetails(@Path("id") leadId: String): LeadDetailsResponse

    @PUT("leads/{id}")
    suspend fun updateLeadDetails(@Path("id") leadId: String, @Body lead: Lead): Lead

    @POST(" /api/leads/convert-lead/{id}")
    suspend fun convertLeadToContact(@Path("id") leadId: String,@Body req: ConvertLeadToContact): LeadResponse

    @POST(" /api/leads/convert-lead/{id}")
    suspend fun convertLeadToDeal(@Path("id") leadId: String,@Body req: ConvertLeadToDeal): LeadResponse
    @GET("/api/contacts/agent/{id}")
    suspend fun getContacts(@Path("id") agentId : String): ContactResponse

    @GET("/api/contacts/template")
    suspend fun getContactTemplate(): TemplateResponse

    @POST("/api/contacts/create")
    suspend fun createContact(@Body req: CreateContactReq): ContactResponse

    @POST("/api/contacts/create")
    suspend fun createContact1(@Body jsonObject: JsonObject): ContactResponse

    @GET("/api/contacts/get-contact/{id}")
    suspend fun getContactDetails(@Path("id") leadId: String): ContactDetailsResponse

    @GET("/api/deals/get")
    suspend fun getDeals(): DealResponse

    @GET("/api/deals/get-one/{id}")
    suspend fun getDealDetails(@Path("id") leadId: String): DealDetailResponse

    @POST("/api/deals/create")
    suspend fun createDeal(@Body jsonObject: JsonObject): LeadResponse

    @GET("/api/deals/template")
    suspend fun getDealTemplate(): TemplateResponse
    @GET("/api/messages/active-conversations/chat")
    suspend fun getConversations(): Chat

    @GET("/api/messages/{id}")
    suspend fun getMessagesList(@Path("id") id: String): MessagesResponse

    @Multipart
    @POST("/api/messages/send")
    suspend fun sendMessage(
        @Part("receiverId") receiverId: RequestBody,
        @Part("text") text: RequestBody,
    ): SendMessageResponse

    @POST("/api/activities/create/task")
    suspend fun createTask(@Body jsonObject: JsonObject): LeadResponse

    @GET("/api/tasks/template")
    suspend fun getTaskTemplate(): TemplateResponse

    @POST("/api/activities/create/meeting")
    suspend fun createMeeting(@Body jsonObject: JsonObject): LeadResponse

    @GET("/api/meetings/template")
    suspend fun getMeetingTemplate(): TemplateResponse

    @POST("/api/activities/create/call")
    suspend fun createCall(@Body jsonObject: JsonObject): LeadResponse

    @GET("/api/calls/template")
    suspend fun getCallTemplate(): TemplateResponse

    @GET("/api/activities/{module}/{zohoId}")
    suspend fun getActivities(@Path("module") module: String,@Path("zohoId") id: String): ActivitiesResponse

    @GET("/api/activities/get/meetings/{id}")
    suspend fun getMeetingDetails(@Path("id") id: String): MeetingsDetails

    @PUT("/api/activities/update/meeting/{id}")
    suspend fun updateMeetingDetails(@Path("id") id: String,@Body jsonObject: JsonObject): UpdateMeeting

    @GET("/api/activities/get/calls/{id}")
    suspend fun getCallsDetails(@Path("id") id: String): CallsDetails

    @PUT("/api/activities/update/call/{id}")
    suspend fun updateCallDetails(@Path("id") id: String,@Body jsonObject: JsonObject): UpdateMeeting

    @GET("/api/activities/get/tasks/{id}")
    suspend fun getTasksDetails(@Path("id") id: String): TasksDetails

    @PUT("/api/activities/update/task/{id}")
    suspend fun updateTaskDetails(@Path("id") id: String,@Body jsonObject: JsonObject): UpdateMeeting

    @GET("/api/leads/get-lead-template/{id}")
    suspend fun getLeadsDetails(@Path("id") id: String): EditTemplateResponse

    @GET("/api/contacts/get-contact-template/{id}")
    suspend fun getContactsDetails(@Path("id") id: String): EditTemplateResponse

    @GET("/api/deals/get-deal-template/{id}")
    suspend fun getEditDealDetails(@Path("id") id: String): EditTemplateResponse
}