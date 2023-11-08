package com.app.pract_spring_auth.model.user.role

import java.io.Serializable

class UserRolePK(
    val id: Long? = null,
    val roleName: EUserRole = EUserRole.USER
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserRolePK

        if (id != other.id) return false
        if (roleName != other.roleName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + roleName.hashCode()
        return result
    }
}
