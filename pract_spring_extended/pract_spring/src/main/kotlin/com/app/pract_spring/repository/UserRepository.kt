package com.app.pract_spring.repository

import com.app.pract_spring.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>
}