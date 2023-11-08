package com.app.pract_spring_auth.model.user

import com.app.pract_spring_auth.payload.user.response.UserInfoResponse
import com.app.pract_spring_auth.model.user.role.UserRole
import com.app.pract_spring_auth.service.UserDetailsImpl
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@Table(name = "ps_user", schema = "pract_spring_database")
class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "username", length = 64, nullable = false)
    var username: String? = null

    @Column(name = "display_name", length = 32, nullable = false)
    var displayName: String? = null

    @Column(name = "email", length = 320, nullable = false)
    var email: String? = null

    @Column(name = "password", nullable = false)
    var password: String? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    var roles: MutableList<UserRole> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =  this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    fun toResponse(): UserInfoResponse =
        UserInfoResponse(
            id = this.id!!,
            username = this.username!!,
            displayName = this.displayName!!,
            roleNames = this.roles.map { it.roleName }.toList()
        )

    fun toDetails(): UserDetailsImpl {
        val authorities: List<GrantedAuthority> = roles.map { SimpleGrantedAuthority(it.roleName.name) }

        return UserDetailsImpl().also {
            it.id = id
            it.username = username
            it.email = email
            it.password = password
            it.authorities = authorities.toMutableSet()
        }
    }
}
