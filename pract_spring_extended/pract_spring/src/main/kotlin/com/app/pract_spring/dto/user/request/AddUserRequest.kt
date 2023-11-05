package com.app.pract_spring.dto.user.request

data class AddUserRequest(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String,
    val roles: List<String>
)