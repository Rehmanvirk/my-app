package com.app.calllogs.di.data

import com.google.gson.annotations.SerializedName


data class ContactResponse(
    val message: String,
    val count : Int?=null,
    val contacts: List<Contacts>? = null,
)

data class Contacts(
    val _id: String,
    val zohoContactId : String,
    val Email: String,
    val Full_Name: String?=null,
    val ProductImage: String?=null,  // used for placeholder avatar initials
    val Last_Name: String?=null,
    val Phone: String?=null,
    val First_Name: String,
    val Lead_Source: String,
    val updatedAt: String,
    val raw : Raw

)

data class CreateContactReq(
    val First_Name: String,
    val Last_Name: String?=null,
    val Phone: String?=null,
    val Email: String,

)

data class ContactDetailsResponse(
    val message: String,
    val contact: Contacts? = null,
)

data class Raw(
    @SerializedName("Owner")
    val owner: UserRef?,

    @SerializedName("agent")
    val agent: AgentRef?,

    @SerializedName("Email")
    val email: String?,

    @SerializedName("\$currency_symbol")
    val currencySymbol: String?,

    @SerializedName("\$field_states")
    val fieldStates: String?,

    @SerializedName("Other_Phone")
    val otherPhone: String?,

    @SerializedName("Mailing_State")
    val mailingState: String?,

    @SerializedName("Other_State")
    val otherState: String?,

    @SerializedName("\$sharing_permission")
    val sharingPermission: String?,

    @SerializedName("Other_Country")
    val otherCountry: String?,

    @SerializedName("Last_Activity_Time")
    val lastActivityTime: String?, // can be String? if sometimes present

    @SerializedName("Department")
    val department: String?,

    @SerializedName("Unsubscribed_Mode")
    val unsubscribedMode: String?,

    @SerializedName("\$process_flow")
    val processFlow: Boolean?,

    @SerializedName("Assistant")
    val assistant: String?,

    @SerializedName("Mailing_Country")
    val mailingCountry: String?,

    @SerializedName("\$locked_for_me")
    val lockedForMe: Boolean?,

    @SerializedName("id")
    val id: String?,

    @SerializedName("Reporting_To")
    val reportingTo: String?,

    @SerializedName("\$approval")
    val approval: Approval?,

    @SerializedName("Enrich_Status__s")
    val enrichStatusS: String?,

    @SerializedName("Other_City")
    val otherCity: String?,

    @SerializedName("Created_Time")
    val createdTime: String?,

    @SerializedName("Change_Log_Time__s")
    val changeLogTimeS: String?,

    @SerializedName("\$wizard_connection_path")
    val wizardConnectionPath: String?,

    @SerializedName("\$editable")
    val editable: Boolean?,

    @SerializedName("Home_Phone")
    val homePhone: String?,

    @SerializedName("Created_By")
    val createdBy: UserRef?,

    @SerializedName("\$zia_owner_assignment")
    val ziaOwnerAssignment: String?,

    @SerializedName("Secondary_Email")
    val secondaryEmail: String?,

    @SerializedName("\$is_duplicate")
    val isDuplicate: Boolean?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("Vendor_Name")
    val vendorName: String?,

    @SerializedName("Mailing_Zip")
    val mailingZip: String?,

    @SerializedName("\$review_process")
    val reviewProcess: ReviewProcess?,

    @SerializedName("Other_Zip")
    val otherZip: String?,

    @SerializedName("Twitter")
    val twitter: String?,

    @SerializedName("Mailing_Street")
    val mailingStreet: String?,

    @SerializedName("\$layout_id")
    val layoutId: LayoutId?,

    @SerializedName("Salutation")
    val salutation: String?,

    @SerializedName("First_Name")
    val firstName: String?,

    @SerializedName("Asst_Phone")
    val asstPhone: String?,

    @SerializedName("Full_Name")
    val fullName: String?,

    @SerializedName("Record_Image")
    val recordImage: String?,

    @SerializedName("Modified_By")
    val modifiedBy: UserRef?,

    @SerializedName("\$review")
    val review: String?,

    @SerializedName("Skype_ID")
    val skypeId: String?,

    @SerializedName("Phone")
    val phone: String?,

    @SerializedName("Account_Name")
    val accountName: NameIdRef?,

    @SerializedName("Email_Opt_Out")
    val emailOptOut: Boolean?,

    @SerializedName("\$zia_visions")
    val ziaVisions: String?,

    @SerializedName("Modified_Time")
    val modifiedTime: String?,

    @SerializedName("Date_of_Birth")
    val dateOfBirth: String?,

    @SerializedName("Mailing_City")
    val mailingCity: String?,

    @SerializedName("Unsubscribed_Time")
    val unsubscribedTime: String?,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Other_Street")
    val otherStreet: String?,

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
    val lastEnrichedTimeS: String?
)

data class UserRef(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("email") val email: String?
)

data class AgentRef(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?
)

data class NameIdRef(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?
)

data class Approval1(
    @SerializedName("delegate") val delegate: Boolean?,
    @SerializedName("takeover") val takeover: Boolean?,
    @SerializedName("approve") val approve: Boolean?,
    @SerializedName("reject") val reject: Boolean?,
    @SerializedName("resubmit") val resubmit: Boolean?
)

data class ReviewProcess1(
    @SerializedName("approve") val approve: Boolean?,
    @SerializedName("reject") val reject: Boolean?,
    @SerializedName("resubmit") val resubmit: Boolean?
)

data class LayoutId1(
    @SerializedName("display_label") val displayLabel: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?
)