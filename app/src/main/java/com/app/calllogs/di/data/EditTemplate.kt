package com.app.calllogs.di.data


data class EditTemplate(
    val recordId: String,
    val zohoLeadId: String,
    val moduleName: String,
    val fields: List<EditFields>

)

data class EditTemplateResponse(
    val message: String,
    val data: EditTemplate
)

data class EditFields(
    val apiName: String,
    val label: String,
    val value: Any?="",
    val dataType: String,
    val required: Boolean,
    val options: List<String>? = emptyList(),
    val _id: String,
)

//sealed class FieldValue {
//    data class Text(val value: String) : FieldValue()
//    data class DateTime(val millis: Long?) : FieldValue() // store as epoch millis
//
//    data class Date(val millis: Long?) : FieldValue()
//    data class Pick(val value: String?) : FieldValue()
//
//    data class Select(val value: Boolean?) : FieldValue()
//}
