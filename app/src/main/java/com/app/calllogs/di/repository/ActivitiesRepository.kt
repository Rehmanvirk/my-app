package com.app.calllogs.di.repository

import com.app.calllogs.di.data.ActivitiesResponse
import com.app.calllogs.di.data.CallsDetails
import com.app.calllogs.di.data.MeetingsDetails
import com.app.calllogs.di.data.TasksDetails
import com.app.calllogs.di.data.UpdateMeeting
import com.app.calllogs.di.network.ApiResult
import com.google.gson.JsonObject

interface ActivitiesRepository {
    suspend fun getActivities(module : String,zohoId : String): ApiResult<ActivitiesResponse>

    suspend fun getMeetingDetails(id : String): ApiResult<MeetingsDetails>

    suspend fun updateMeetingDetails(id : String,jsonObject: JsonObject): ApiResult<UpdateMeeting>

    suspend fun getCallDetails(id : String): ApiResult<CallsDetails>

    suspend fun updateCallDetails(id : String,jsonObject: JsonObject): ApiResult<UpdateMeeting>
    suspend fun getTaskDetails(id : String): ApiResult<TasksDetails>

    suspend fun updateTaskDetails(id : String,jsonObject: JsonObject): ApiResult<UpdateMeeting>
}