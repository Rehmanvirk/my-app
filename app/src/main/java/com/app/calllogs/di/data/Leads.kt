package com.app.calllogs.di.data

import com.google.gson.annotations.SerializedName

//enum class LeadStatus { ALL, NEW, HOT, CONTACTED, CLOSED, IN_PROGRESS }
enum class LeadStatus { ALL, AttemptedtoContact,
    ContactinFuture,
    Contacted,
    Junklead,
    Lostlead,
    Notcontacted,
    Prequalified,
    Notqualified
}

data class Lead(
    val _id: String,
    val zohoLeadId: String,
    @SerializedName("dynamicFields")
    val dynamicFields : DynamicFields,
    val updatedAt: String,  // e.g. "2h ago", "Oct 24"
)

data class LeadResponse(
    val message: String,
    val count : Int?=null,
    val leads: List<Lead>? = null,
)

data class LeadDetailsResponse(
    val message: String,
    val lead: Lead? = null,
)
data class DynamicFieldsOld(
    val Email: String?=null,
    val Full_Name: String,
    @SerializedName("Lead_Status")
    val Lead_Status: String?=null,
    val ProductImage: String?=null,  // used for placeholder avatar initials
    val Last_Name: String?=null,
    val Phone: String?=null,
    val Lead_Source: String?=null,   // e.g. "Website", "Referral"
    val First_Name: String?=null,
    val priority: String?=null,
    val activityHistory: List<Activity>?=null
)
data class DynamicFields(
    @SerializedName("Company")
    val company: String?,

    @SerializedName("agent")
    val agent: Agent?,

    @SerializedName("Email")
    val email: String?,

    @SerializedName($$"$currency_symbol")
    val currencySymbol: String?,

    @SerializedName("\$field_states")
    val fieldStates: String?,

    @SerializedName("\$sharing_permission")
    val sharingPermission: String?,

    @SerializedName("Last_Activity_Time")
    val lastActivityTime: String?,

    @SerializedName("Industry")
    val industry: String?,

    @SerializedName("Unsubscribed_Mode")
    val unsubscribedMode: String?,

    @SerializedName("\$process_flow")
    val processFlow: Boolean?,

    @SerializedName("Street")
    val street: String?,

    @SerializedName("\$locked_for_me")
    val lockedForMe: Boolean?,

    @SerializedName("Zip_Code")
    val zipCode: String?,

    @SerializedName("\$approval")
    val approval: Approval?,

    @SerializedName("Enrich_Status__s")
    val enrichStatusS: String?,

    @SerializedName("Created_Time")
    val createdTime: String?,

    @SerializedName("Change_Log_Time__s")
    val changeLogTimeS: String?,

    @SerializedName("\$wizard_connection_path")
    val wizardConnectionPath: String?,

    @SerializedName("\$editable")
    val editable: Boolean?,

    @SerializedName("City")
    val city: String?,

    @SerializedName("No_of_Employees")
    val noOfEmployees: Int?,

    @SerializedName("Converted__s")
    val convertedS: Boolean?,

    @SerializedName("Converted_Date_Time")
    val convertedDateTime: String?,

    @SerializedName("Converted_Account")
    val convertedAccount: String?,

    @SerializedName("State")
    val state: String?,

    @SerializedName("Country")
    val country: String?,

    @SerializedName("\$zia_owner_assignment")
    val ziaOwnerAssignment: String?,

    @SerializedName("Annual_Revenue")
    val annualRevenue: String?,

    @SerializedName("Secondary_Email")
    val secondaryEmail: String?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("ProductImage")
    val productImage: String?,

    @SerializedName("Rating")
    val rating: String?,

    @SerializedName("\$review_process")
    val reviewProcess: ReviewProcess?,

    @SerializedName("Website")
    val website: String?,

    @SerializedName("Twitter")
    val twitter: String?,

    @SerializedName("\$layout_id")
    val layoutId: LayoutId?,

    @SerializedName("Salutation")
    val salutation: String?,

    @SerializedName("First_Name")
    val firstName: String?,

    @SerializedName("Lead_Status")
    val leadStatus: String?,

    @SerializedName("Full_Name")
    val fullName: String?,

    @SerializedName("Record_Image")
    val recordImage: String?,

    @SerializedName("Converted_Deal")
    val convertedDeal: String?,

    @SerializedName("\$review")
    val review: String?,

    @SerializedName("Lead_Conversion_Time")
    val leadConversionTime: String?,

    @SerializedName("Skype_ID")
    val skypeId: String?,

    @SerializedName("Phone")
    val phone: String?,

    @SerializedName("Email_Opt_Out")
    val emailOptOut: Boolean?,

    @SerializedName("\$zia_visions")
    val ziaVisions: String?,

    @SerializedName("Extra_desc")
    val extraDesc: String?,

    @SerializedName("Designation")
    val designation: String?,

    @SerializedName("Modified_Time")
    val modifiedTime: String?,

    @SerializedName("Unsubscribed_Time")
    val unsubscribedTime: String?,

    @SerializedName("Converted_Contact")
    val convertedContact: String?,

    @SerializedName("Mobile")
    val mobile: String?,

    @SerializedName("Record_Status__s")
    val recordStatusS: String?,

    @SerializedName("\$orchestration")
    val orchestration: Boolean?,

    @SerializedName("Last_Name")
    val lastName: String?,

    @SerializedName("\$in_merge")
    val inMerge: Boolean?,

    @SerializedName("Locked__s")
    val lockedS: Boolean?,

    @SerializedName("Lead_Source")
    val leadSource: String?,

    @SerializedName("Tag")
    val tag: List<String>?,

    @SerializedName("Fax")
    val fax: String?,

    @SerializedName("\$approval_state")
    val approvalState: String?,

    @SerializedName("\$pathfinder")
    val pathfinder: Boolean?,

    @SerializedName("Last_Enriched_Time__s")
    val lastEnrichedTimeS: String?,
    val priority: String?=null,
    val activityHistory: List<Activity>?=null
)

data class Agent(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: String?
)

data class Approval(
    @SerializedName("delegate")
    val delegate: Boolean?,
    @SerializedName("takeover")
    val takeover: Boolean?,
    @SerializedName("approve")
    val approve: Boolean?,
    @SerializedName("reject")
    val reject: Boolean?,
    @SerializedName("resubmit")
    val resubmit: Boolean?
)

data class ReviewProcess(
    @SerializedName("approve")
    val approve: Boolean?,
    @SerializedName("reject")
    val reject: Boolean?,
    @SerializedName("resubmit")
    val resubmit: Boolean?
)

data class LayoutId(
    @SerializedName("display_label")
    val displayLabel: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: String?
)

data class CreateLeadRequest(
    val dynamicFields : DynamicFields,
)

data class ConvertLeadToContact(
    val createDeal : Boolean,
    val moveAttachmentsTo : String = "Contacts"
)

data class ConvertLeadToDeal(
    val createDeal : Boolean,
    val moveAttachmentsTo : String = "Deals",
    val dealData: DealData
)

data class DealData(
    val Deal_Name : String
)
data class Activity(
    val activityType: String,
    val description: String,
    val timestamp: String
)