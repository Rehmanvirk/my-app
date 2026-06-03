package com.app.calllogs.di.repository

import com.app.calllogs.di.data.ContactDetailsResponse
import com.app.calllogs.di.data.ContactResponse
import com.app.calllogs.di.data.CreateContactReq
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.network.ApiResult

interface ContactsRepository {
    suspend fun getContacts(id : String): ApiResult<ContactResponse>

    suspend fun createContact(req: CreateContactReq):  ApiResult<ContactResponse>

    suspend fun getContactDetails(id : String): ApiResult<ContactDetailsResponse>
}