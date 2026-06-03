package com.app.calllogs.di.data

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user: UserDto? = null,
    val accessToken : String
)

data class UserDto(
    val id: String? = null,
    val role: String? = null,
    val email: String? = null,
    val orgName: String? = null
)