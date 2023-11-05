package com.app.pract_spring.service

import com.app.pract_spring.dto.user.response.UserResponse
import com.app.pract_spring.model.user.User
import com.app.pract_spring.model.user.role.EUserRole
import com.app.pract_spring.model.user.role.UserRole
import com.app.pract_spring.repository.UserRepository
import com.app.pract_spring.repository.UserRoleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository
) {
    fun findUserById(id: Long): User = userRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "The user with id = $id does not exist.")
    }

    fun saveUser(user: User, roles: List<String>): User {
        val savedUser: User

        if (userRepository.findByUsername(user.username!!).isEmpty)
            savedUser = userRepository.save(user)
        else
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified username is already in use.")

        for (roleName in roles) addRoleToUser(savedUser, roleName)

        return savedUser
    }

    private fun addRoleToUser(user: User, roleName: String): UserRole =
        userRoleRepository.save(
            UserRole(
                id = user.id!!,
                roleName = EUserRole.valueOf(roleName)
            )
        )

    fun findAllUsers(): List<User> = userRepository.findAll()
}