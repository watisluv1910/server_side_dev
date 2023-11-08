package com.app.pract_spring_auth.service

import com.app.pract_spring_auth.model.user.User
import com.app.pract_spring_auth.model.user.role.EUserRole
import com.app.pract_spring_auth.model.user.role.UserRole
import com.app.pract_spring_auth.payload.MessageResponse
import com.app.pract_spring_auth.payload.user.request.LoginRequest
import com.app.pract_spring_auth.payload.user.request.RegisterRequest
import com.app.pract_spring_auth.payload.user.response.LoginResponse
import com.app.pract_spring_auth.payload.user.response.UserInfoResponse
import com.app.pract_spring_auth.repository.UserRepository
import com.app.pract_spring_auth.repository.UserRoleRepository
import com.app.pract_spring_auth.security.token.TokenUtils
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.AbstractPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    val userRepository: UserRepository,
    val userRoleRepository: UserRoleRepository,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
    val tokenUtils: TokenUtils
) {

    fun register(request: RegisterRequest): MessageResponse {
        val (user, roleNames) = request.toModel()

        val savedUser: User

        if (userRepository.findByUsername(user.username!!).isEmpty)
            savedUser = userRepository.save(user.apply { password = passwordEncoder.encode(password) })
        else
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "The username ${user.username} is already in use."
            )

        for (roleName in roleNames) addRoleToUser(savedUser, roleName)

        return MessageResponse(
            "The user with id ${savedUser.id} and username ${savedUser.username} registered successfully."
        )
    }

    fun login(request: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val userDetails = authentication.principal as UserDetailsImpl

        val foundUser = userRepository.findByUsername(userDetails.username!!).get()

        return LoginResponse(
            foundUser.toResponse(),
            tokenUtils.generateToken(
                mapOf("roles" to foundUser.roles, "email" to foundUser.email!!),
                userDetails
            )
        )
    }

    private fun addRoleToUser(user: User, roleName: String): UserRole =
        userRoleRepository.save(
            UserRole(
                id = user.id!!,
                roleName = EUserRole.valueOf(roleName)
            )
        )
}