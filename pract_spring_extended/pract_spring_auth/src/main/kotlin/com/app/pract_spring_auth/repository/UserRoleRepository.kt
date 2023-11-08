package com.app.pract_spring_auth.repository

import com.app.pract_spring_auth.model.user.role.UserRole
import com.app.pract_spring_auth.model.user.role.UserRolePK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository: JpaRepository<UserRole, UserRolePK>