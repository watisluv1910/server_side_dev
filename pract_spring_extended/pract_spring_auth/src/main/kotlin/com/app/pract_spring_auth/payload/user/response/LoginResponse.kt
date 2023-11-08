package com.app.pract_spring_auth.payload.user.response

data class LoginResponse(
    val user: UserInfoResponse,
    val accessToken: String
)
