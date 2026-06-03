package com.app.calllogs.di.data

import com.google.gson.annotations.SerializedName

// Data Class

data class DealResponse(
    val total : Int?=null,
    val deals: List<Deal>? = null,
)

data class DealDetailResponse(
    val deal: Deal? = null,
)
data class Deal(
    val _id: String,
    val zohoDealId: String,
    val Deal_Name: String?=null,
    val Amount: String?=null,
    val Stage: String?=null,
    val createdAt: String,
    val progress: Float?=null,
    @SerializedName("raw")
    val raw: RawDeals?=null,
)

data class RawDeals(
    @SerializedName("Description")
    val description: String? = null,

    @SerializedName("\$currency_symbol")
    val currencySymbol: String? = null,

    @SerializedName("Campaign_Source")
    val campaignSource: String? = null,

    @SerializedName("\$field_states")
    val fieldStates: Any? = null, // Type Any if structure is unknown/null

    @SerializedName("\$sharing_permission")
    val sharingPermission: String? = null,

    @SerializedName("Closing_Date")
    val closingDate: String? = null, // "2026-01-23"

    @SerializedName("Last_Activity_Time")
    val lastActivityTime: String? = null, // "2026-01-29T21:57:58+05:00"

    @SerializedName("Reason_For_Loss__s")
    val reasonForLossS: String? = null,

    @SerializedName("\$review")
    val review: Any? = null,

    @SerializedName("Lead_Conversion_Time")
    val leadConversionTime: String? = null,

    @SerializedName("\$process_flow")
    val isProcessFlow: Boolean? = null,

    @SerializedName("Deal_Name")
    val dealName: String? = null,

    @SerializedName("Expected_Revenue")
    val expectedRevenue: Double? = null, // 4938

    @SerializedName("Overall_Sales_Duration")
    val overallSalesDuration: Int? = null, // 1

    @SerializedName("Stage")
    val stage: String? = null, // "Value Proposition"

    @SerializedName("\$locked_for_me")
    val isLockedForMe: Boolean? = null,

    @SerializedName("id")
    val id: String? = null, // "6314862000003793013"

    @SerializedName("\$zia_visions")
    val ziaVisions: Any? = null,

    @SerializedName("Modified_Time")
    val modifiedTime: String? = null, // "2026-01-29T21:57:58+05:00"

    @SerializedName("Created_Time")
    val createdTime: String? = null, // "2026-01-22T17:39:54+05:00"

    @SerializedName("Amount")
    val amount: Long? = null, // 12345

//    @SerializedName("Probability")
    val Probability: Int? = null, // 40

    @SerializedName("Next_Step")
    val nextStep: String? = null,

    @SerializedName("Change_Log_Time__s")
    val changeLogTimeS: String? = null, // "2026-01-29T21:57:58+05:00"

    @SerializedName("\$wizard_connection_path")
    val wizardConnectionPath: Any? = null,

    @SerializedName("\$editable")
    val isEditable: Boolean? = null,

    @SerializedName("Record_Status__s")
    val recordStatusS: String? = null, // "Available"

    @SerializedName("\$orchestration")
    val isOrchestration: Boolean? = null,

    @SerializedName("Pipeline")
    val pipeline: String? = null, // "Standard (Standard)"

    @SerializedName("Sales_Cycle_Duration")
    val salesCycleDuration: Int? = null, // 1

    @SerializedName("Type")
    val type: String? = null,

    @SerializedName("\$in_merge")
    val isInMerge: Boolean? = null,

    @SerializedName("Locked__s")
    val isLockedS: Boolean? = null,

    @SerializedName("Lead_Source")
    val leadSource: String? = null,

    @SerializedName("\$zia_owner_assignment")
    val ziaOwnerAssignment: String? = null,

    @SerializedName("\$approval_state")
    val approvalState: String? = null,

    @SerializedName("\$pathfinder")
    val isPathfinder: Boolean? = null
)