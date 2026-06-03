package com.app.calllogs.di.data


import com.google.gson.annotations.SerializedName

data class Meetings(
    @SerializedName("_id")
    var id: String?,

    @SerializedName("zohoId")
    var zohoId: String?,

    @SerializedName("fetchedByUser")
    var fetchedByUser: String?,

    // top-level agent is an ID string
    @SerializedName("agent")
    var agent: String?,

    // top-level are ISO UTC "Z" strings
    @SerializedName("Start_DateTime")
    var startDateTime: String?,

    @SerializedName("End_DateTime")
    var endDateTime: String?,

    @SerializedName("createdByAgent")
    var createdByAgent: Boolean?,

    @SerializedName("raw")
    var raw: RawMeeting?,

    @SerializedName("createdAt")
    var createdAt: String?,

    @SerializedName("updatedAt")
    var updatedAt: String?,

    @SerializedName("__v")
    var v: Int?,

    @SerializedName("lead")
    var lead: String?
)


data class RawMeeting(
    @SerializedName("All_day")
    var allDay: Boolean?,

    @SerializedName("Owner")
    var owner: UserRef?,

    @SerializedName("Check_In_State")
    var checkInState: String?,

    @SerializedName("\$currency_symbol")
    var currencySymbol: String?,

    @SerializedName("Remind_Participants")
    var remindParticipants: String?,

    @SerializedName("\$field_states")
    var fieldStates: String?,

    @SerializedName("Latitude")
    var latitude: String?, // could be Double? if sometimes numeric

    @SerializedName("\$sharing_permission")
    var sharingPermission: String?,

    @SerializedName("Participants")
    var participants: List<String>?,

    @SerializedName("Last_Activity_Time")
    var lastActivityTime: String?,

    @SerializedName("Online_Meeting_External_UUID__s")
    var onlineMeetingExternalUuidS: String?,

    @SerializedName("\$process_flow")
    var processFlow: Boolean?,

    @SerializedName("Check_In_City")
    var checkInCity: String?,

    @SerializedName("id")
    var rawId: String?,

    @SerializedName("Check_In_Comment")
    var checkInComment: String?,

    @SerializedName("Remind_At")
    var remindAt: String?,

//    @SerializedName("Who_Id")
//    var whoId: String?,

    @SerializedName("Check_In_Status")
    var checkInStatus: String?,

    @SerializedName("\$approvar")
    var approvar: Approval?,

    @SerializedName("Venue")
    var venue: String?,

    @SerializedName("ZIP_Code")
    var zipCode: String?,

    @SerializedName("Created_Time")
    var createdTime: String?,

    @SerializedName("\$editable")
    var editable: Boolean?,

    @SerializedName("\$colour_code")
    var colourCode: String?,

    @SerializedName("Longitude")
    var longitude: String?, // could be Double? if sometimes numeric

    @SerializedName("Check_In_Time")
    var checkInTime: String?,

    @SerializedName("Recurring_Activity")
    var recurringActivity: String?,

    @SerializedName("What_Id")
    var whatId: NameIdRef?,

    @SerializedName("Created_By")
    var createdBy: UserRef?,

    @SerializedName("Meeting_Venue__s")
    var meetingVenueS: String?,

    @SerializedName("Check_In_Address")
    var checkInAddress: String?,

    @SerializedName("Description")
    var description: String?,

    // raw start/end have +05:00 timezone offset strings
    @SerializedName("Start_DateTime")
    var startDateTime: String?,

    @SerializedName("\$review_process")
    var reviewProcess: String?,

    @SerializedName("Event_Title")
    var eventTitle: String?,

    @SerializedName("\$calendar_booking_event")
    var calendarBookingEvent: Boolean?,

    @SerializedName("Check_In_By")
    var checkInBy: String?,

    @SerializedName("End_DateTime")
    var endDateTime: String?,

    @SerializedName("Modified_By")
    var modifiedBy: UserRef?,

    @SerializedName("\$review")
    var review: String?,

    @SerializedName("\$event_cancelled")
    var eventCancelled: Boolean?,

    @SerializedName("\$zia_visions")
    var ziaVisions: String?,

    @SerializedName("Check_In_Country")
    var checkInCountry: String?,

    @SerializedName("Modified_Time")
    var modifiedTime: String?,

    @SerializedName("Meeting_Provider__s")
    var meetingProviderS: String?,

    @SerializedName("\$recurrence_id")
    var recurrenceId: String?,

    @SerializedName("Record_Status__s")
    var recordStatusS: String?,

    @SerializedName("\$orchestration")
    var orchestration: Boolean?,

    @SerializedName("\$se_module")
    var seModule: String?,

    @SerializedName("Check_In_Sub_Locality")
    var checkInSubLocality: String?,

    @SerializedName("\$in_merge")
    var inMerge: Boolean?,

    @SerializedName("\$meeting_details")
    var meetingDetails: String?,

    @SerializedName("\$u_id")
    var uId: String?,

    @SerializedName("Meeting_agent")
    var meetingAgent: String?,

    @SerializedName("Tag")
    var tag: List<String>?,

    @SerializedName("\$send_notification")
    var sendNotification: Boolean?,

    @SerializedName("\$approvar_state")
    var approvarState: String?,

    @SerializedName("\$pathfinder")
    var pathfinder: Boolean?
)

data class MeetingsDetails(
    var message : String,
    var record : Meetings
)

data class UpdateMeeting(
    var message : String,
    var zohoId : String
)

