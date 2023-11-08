package com.app.pract_spring_auth.payload.user.request

data class LoginRequest(
    val username: String,
    val password: String
)
