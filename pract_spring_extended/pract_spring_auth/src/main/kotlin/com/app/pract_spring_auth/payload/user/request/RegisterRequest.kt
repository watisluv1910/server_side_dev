package com.app.pract_spring_auth.payload.user.request

import com.app.pract_spring_auth.model.user.User

data class RegisterRequest(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String,
    val roles: List<String>
) {

    fun toModel(): Pair<User, List<String>> =
        Pair(
            User().also { user ->
                user.username = username
                user.displayName = displayName
                user.email = email
                user.password = password
            },
            roles
        )
}