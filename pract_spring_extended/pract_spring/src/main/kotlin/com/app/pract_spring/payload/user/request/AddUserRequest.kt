package com.app.pract_spring.payload.user.request

data class AddUserRequest(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String,
    val roles: List<String>
)