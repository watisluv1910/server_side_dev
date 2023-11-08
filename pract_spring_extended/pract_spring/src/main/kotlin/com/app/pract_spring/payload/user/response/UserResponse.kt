package com.app.pract_spring.payload.user.response

import com.app.pract_spring.model.user.role.EUserRole
import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    val id: Long,
    val username: String,
    val displayName: String,
    @JsonProperty("roles") val roleNames: List<EUserRole>?
)
