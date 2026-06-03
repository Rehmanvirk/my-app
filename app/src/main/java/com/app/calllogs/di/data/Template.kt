package com.app.calllogs.di.data


data class Template(
    val _id: String,
    val user: String,
    val moduleName: String,
    val fields: List<Fields>

)

data class TemplateResponse(
    val message: String,
    val template: Template
)

data class Fields(
    val apiName: String,
    val label: String,
    val dataType: String,
    val required: Boolean,
    val options: List<String>? = emptyList(),
    val _id: String,
)

sealed class FieldValue {
    data class Text(val value: String) : FieldValue()
    data class DateTime(val millis: Long?) : FieldValue() // store as epoch millis

    data class Date(val millis: Long?) : FieldValue()
    data class Pick(val value: String?) : FieldValue()

    data class Select(val value: Boolean?) : FieldValue()
}
