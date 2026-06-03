package com.app.calllogs.di.data

import kotlin.String

data class MessagesResponse(
    val success : Boolean,
    val messages: List<Messages>
)

data class Messages(
    val id : String,
    val senderId : String,
    val senderName : String,
    val avatar : String,
    val content : String?,
    val file : File?,
    val status : String?,
    val timestamp : String,
)

data class File(
    val fileName : String,
    val filePath : String,
    val fileSize : Int,
    val fileType : String
)
