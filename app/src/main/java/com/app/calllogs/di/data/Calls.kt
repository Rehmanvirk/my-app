package com.app.calllogs.di.data

import com.google.gson.annotations.SerializedName

data class Calls(
    @SerializedName("_id")
    val id: String?,

    @SerializedName("zohoId")
    val zohoId: String?,

    @SerializedName("fetchedByUser")
    val fetchedByUser: String?,

    // Top-level agent is an ID string
    @SerializedName("agent")
    val agent: String?,

    @SerializedName("Subject")
    val subject: String?,

    @SerializedName("Call_Type")
    val callType: String?,

    // Top-level Call_Duration is "15" (string)
    @SerializedName("Call_Duration")
    val callDuration: String?,

    @SerializedName("raw")
    val raw: RawCall?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("__v")
    val v: Int?,

    @SerializedName("lead")
    val lead: String?
)

data class RawCall(
    // In raw it's "00:15"
    @SerializedName("Call_Duration")
    val callDuration: String?,

    @SerializedName("Owner")
    val owner: UserRef?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("\$currency_symbol")
    val currencySymbol: String?,

    @SerializedName("CTI_Entry")
    val ctiEntry: Boolean?,

    @SerializedName("Caller_ID")
    val callerId: String?,

    @SerializedName("\$calendar_booking_call")
    val calendarBookingCall: Boolean?,

    @SerializedName("\$field_states")
    val fieldStates: String?,

    @SerializedName("\$review_process")
    val reviewProcess: String?,

    @SerializedName("\$sharing_permission")
    val sharingPermission: String?,

    @SerializedName("Call_Agenda")
    val callAgenda: String?,

    @SerializedName("Last_Activity_Time")
    val lastActivityTime: String?,

    @SerializedName("Modified_By")
    val modifiedBy: UserRef?,

    @SerializedName("\$telephony_call")
    val telephonyCall: Boolean?,

    @SerializedName("\$review")
    val review: String?,

    @SerializedName("\$process_flow")
    val processFlow: Boolean?,

    @SerializedName("Call_Purpose")
    val callPurpose: String?,

    @SerializedName("id")
    val rawId: String?,

    @SerializedName("\$zia_visions")
    val ziaVisions: String?,

//    @SerializedName("Who_Id")
//    val whoId: String?,

    @SerializedName("Outgoing_Call_Status")
    val outgoingCallStatus: String?,

    @SerializedName("\$approval")
    val approval: Approval?,

    @SerializedName("Modified_Time")
    val modifiedTime: String?,

    @SerializedName("Reminder")
    val reminder: String?,

    @SerializedName("Voice_Recording__s")
    val voiceRecordingS: String?,

    @SerializedName("Created_Time")
    val createdTime: String?,

    @SerializedName("Call_Start_Time")
    val callStartTime: String?,

    @SerializedName("\$editable")
    val editable: Boolean?,

    @SerializedName("Record_Status__s")
    val recordStatusS: String?,

    @SerializedName("Subject")
    val subject: String?,

    @SerializedName("Call_agent")
    val callAgent: String?,

    @SerializedName("\$orchestration")
    val orchestration: Boolean?,

    @SerializedName("\$se_module")
    val seModule: String?,

    @SerializedName("Call_Type")
    val callType: String?,

    @SerializedName("Call_Result")
    val callResult: String?,

    @SerializedName("Scheduled_In_CRM")
    val scheduledInCrm: String?,

    @SerializedName("What_Id")
    val whatId: NameIdRef?,

    @SerializedName("\$in_merge")
    val inMerge: Boolean?,

    @SerializedName("Call_Duration_in_seconds")
    val callDurationInSeconds: Int?,

    @SerializedName("Created_By")
    val createdBy: UserRef?,

    @SerializedName("Tag")
    val tag: List<String>?,

    @SerializedName("Dialled_Number")
    val dialledNumber: String?,

    @SerializedName("\$approval_state")
    val approvalState: String?,

    @SerializedName("\$pathfinder")
    val pathfinder: Boolean?
)

data class CallsDetails(
    val message : String,
    val record : Calls
)