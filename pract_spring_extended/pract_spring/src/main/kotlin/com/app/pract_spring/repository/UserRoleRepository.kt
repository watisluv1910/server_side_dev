package com.app.pract_spring.repository

import com.app.pract_spring.model.user.role.UserRole
import com.app.pract_spring.model.user.role.UserRolePK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository: JpaRepository<UserRole, UserRolePK>