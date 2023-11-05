package com.app.pract_spring.controller

import com.app.pract_spring.dto.user.request.AddUserRequest
import com.app.pract_spring.dto.user.response.UserResponse
import com.app.pract_spring.model.user.User
import com.app.pract_spring.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody request: AddUserRequest) =
        ResponseEntity<UserResponse>(
            userService.saveUser(
                user = request.toModel().first,
                roles = request.toModel().second).toResponse(),
            HttpStatus.CREATED
        )

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> =
        ResponseEntity<List<UserResponse>>(
            userService.findAllUsers().map { it.toResponse() },
            HttpStatus.OK
        )

    @GetMapping("/{id}")
    fun getUserById(@PathVariable(name = "id") id: Long) =
        ResponseEntity<UserResponse>(
            userService.findUserById(id).toResponse(),
            HttpStatus.OK
        )

    private fun AddUserRequest.toModel(): Pair<User, List<String>> =
        Pair(
            User().also {user ->
                user.username = username
                user.displayName = displayName
                user.email = email
                user.password = password
            },
            roles
        )

    private fun User.toResponse(): UserResponse =
        UserResponse(
            id = this.id!!,
            username = this.username!!,
            displayName = this.displayName!!,
            email = this.email!!,
            roleNames = this.roles.map { it.roleName }.toList()
        )
}

