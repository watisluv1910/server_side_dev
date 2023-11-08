package com.app.pract_spring_auth.payload.user.response

import com.app.pract_spring_auth.model.user.role.EUserRole
import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoResponse(
    val id: Long,
    val username: String,
    val displayName: String,
    @JsonProperty("roles") val roleNames: List<EUserRole>?
)
