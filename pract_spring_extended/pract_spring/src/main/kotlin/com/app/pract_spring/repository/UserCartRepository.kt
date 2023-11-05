package com.app.pract_spring.repository

import com.app.pract_spring.model.user.cart.UserCart
import com.app.pract_spring.model.user.cart.UserCartPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCartRepository: JpaRepository<UserCart, UserCartPK> {

    fun deleteAllByUserId(userId: Long): List<UserCart>
}