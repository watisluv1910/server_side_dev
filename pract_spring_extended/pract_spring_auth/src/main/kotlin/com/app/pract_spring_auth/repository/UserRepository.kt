package com.app.pract_spring_auth.repository

import com.app.pract_spring_auth.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>
}