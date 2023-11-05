package com.app.pract_spring.model.user.cart

import java.io.Serializable

class UserCartPK (
    val userId: Long? = null,
    val productBaseId: Long? = null
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCartPK

        if (userId != other.userId) return false
        if (productBaseId != other.productBaseId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId?.hashCode() ?: 0
        result = 31 * result + (productBaseId?.hashCode() ?: 0)
        return result
    }
}
