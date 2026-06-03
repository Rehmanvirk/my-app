package com.app.calllogs.di.repository

import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DealDetailResponse
import com.app.calllogs.di.data.DealResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.network.ApiResult

interface DealsRepository {
    suspend fun getDeals(): ApiResult<DealResponse>

    suspend fun getDealDetail(id : String): ApiResult<DealDetailResponse>
}