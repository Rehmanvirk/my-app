package com.app.calllogs.di.data

import com.google.gson.annotations.SerializedName


data class ActivitiesResponse(
    val tasks : List<Tasks>?= emptyList(),
    val calls : List<Calls>?= emptyList(),
    val meetings : List<Meetings>?= emptyList(),
    val notes : List<Tasks>?= emptyList()
)

data class Tasks(
    @SerializedName("_id")
    val id: String?,

    @SerializedName("zohoId")
    val zohoId: String?,

    @SerializedName("fetchedByUser")
    val fetchedByUser: String?,

    @SerializedName("agent")
    val agent: String?,

    @SerializedName("raw")
    val raw: RawTask?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("__v")
    val v: Int?,

    @SerializedName("lead")
    val lead: String?
)

data class RawTask(
    @SerializedName("Owner")
    val owner: UserRef?,

    // NOTE: in this JSON, "agent" is a String (not object)
    @SerializedName("agent")
    val agent: String?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("\$currency_symbol")
    val currencySymbol: String?,

    @SerializedName("\$field_states")
    val fieldStates: String?,

    @SerializedName("Closed_Time")
    val closedTime: String?,

    @SerializedName("\$review_process")
    val reviewProcess: String?,

    @SerializedName("\$sharing_permission")
    val sharingPermission: String?,

    @SerializedName("Last_Activity_Time")
    val lastActivityTime: String?,

    @SerializedName("Send_Notification_Email")
    val sendNotificationEmail: Boolean?,

    @SerializedName("Modified_By")
    val modifiedBy: UserRef?,

    @SerializedName("\$review")
    val review: String?,

    @SerializedName("\$process_flow")
    val processFlow: Boolean?,

    @SerializedName("\$locked_for_me")
    val lockedForMe: Boolean?,

    @SerializedName("id")
    val rawId: String?,

    @SerializedName("\$zia_visions")
    val ziaVisions: String?,

    @SerializedName("Remind_At")
    val remindAt: String?,

//    @SerializedName("Who_Id")
//    val whoId: String?,

    @SerializedName("Status")
    val status: String?,

    @SerializedName("\$approval")
    val approval: Approval?,

    @SerializedName("Modified_Time")
    val modifiedTime: String?,

    @SerializedName("Due_Date")
    val dueDate: String?,

    @SerializedName("Priority")
    val priority: String?,

    @SerializedName("Created_Time")
    val createdTime: String?,

    @SerializedName("\$editable")
    val editable: Boolean?,

    @SerializedName("Record_Status__s")
    val recordStatusS: String?,

    @SerializedName("Subject")
    val subject: String?,

    @SerializedName("\$orchestration")
    val orchestration: Boolean?,

    @SerializedName("\$se_module")
    val seModule: String?,

    @SerializedName("Recurring_Activity")
    val recurringActivity: String?,

    @SerializedName("What_Id")
    val whatId: NameIdRef?,

    @SerializedName("\$in_merge")
    val inMerge: Boolean?,

    @SerializedName("\$u_id")
    val uId: String?,

    @SerializedName("Locked__s")
    val lockedS: Boolean?,

    @SerializedName("Created_By")
    val createdBy: UserRef?,

    @SerializedName("Tag")
    val tag: List<String>?,

    @SerializedName("\$zia_owner_assignment")
    val ziaOwnerAssignment: String?,

    @SerializedName("\$approval_state")
    val approvalState: String?,

    @SerializedName("\$pathfinder")
    val pathfinder: Boolean?
)

data class TasksDetails(
    val message : String,
    val record : Tasks
)



