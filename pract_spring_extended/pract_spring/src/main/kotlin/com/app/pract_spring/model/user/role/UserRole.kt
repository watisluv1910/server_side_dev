package com.app.pract_spring.model.user.role

import com.app.pract_spring.model.user.User
import jakarta.persistence.*

@Entity
@IdClass(UserRolePK::class)
@Table(name = "ps_user_roles", schema = "pract_spring_database")
data class UserRole (

    @Id
    @Column(name = "user_id", nullable = false)
    var id: Long,

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role_name", nullable = false)
    var roleName: EUserRole
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false
    )
    lateinit var user: User
}
