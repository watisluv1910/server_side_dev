package com.app.pract_spring_auth.controller

import com.app.pract_spring_auth.payload.MessageResponse
import com.app.pract_spring_auth.payload.user.request.LoginRequest
import com.app.pract_spring_auth.payload.user.request.RegisterRequest
import com.app.pract_spring_auth.payload.user.response.LoginResponse
import com.app.pract_spring_auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) =
        ResponseEntity<MessageResponse>(
            authService.register(request),
            HttpStatus.CREATED
        )

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) =
        ResponseEntity<LoginResponse>(
            authService.login(request),
            HttpStatus.ACCEPTED
        )
}