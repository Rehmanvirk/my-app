package com.app.calllogs.di.data

data class Chat(
    val success: Boolean,
    val conversations: List<Conversations>
)

data class Conversations(
    val _id: String,
    val type: String,
    val lastMessage: LastMessage
)

data class LastMessage(
    val _id: String,
    val text: String?,
    val file : File?,
    val status: String,
    val sender: Sender,
    val createdAt : String,
)

data class Sender(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val status: String
)

data class SendMessageResponse(
    val success: Boolean,
    val messages: Messages
)