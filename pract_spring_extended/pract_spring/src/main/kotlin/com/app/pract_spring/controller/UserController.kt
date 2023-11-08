package com.app.pract_spring.controller

import com.app.pract_spring.payload.user.response.UserResponse
import com.app.pract_spring.model.user.User
import com.app.pract_spring.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [
    "/api/v1/adminboard/users"
])
class UserController(
    val userService: UserService
) {

    // By Admin
    @GetMapping
    fun getAllUsers() =
        ResponseEntity<List<UserResponse>>(
            userService.findAllUsers().map { it.toResponse() },
            HttpStatus.OK
        )

    // By Admin
    @GetMapping("/{id}")
    fun getUserById(@PathVariable(name = "id") id: Long) =
        ResponseEntity<UserResponse>(
            userService.findUserById(id).toResponse(),
            HttpStatus.OK
        )

    private fun User.toResponse(): UserResponse =
        UserResponse(
            id = this.id!!,
            username = this.username!!,
            displayName = this.displayName!!,
            roleNames = this.roles.map { it.roleName }.toList()
        )
}

