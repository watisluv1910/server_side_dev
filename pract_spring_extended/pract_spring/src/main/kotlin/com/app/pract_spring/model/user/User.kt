package com.app.pract_spring.model.user

import com.app.pract_spring.model.product.ProductBase
import com.app.pract_spring.model.user.role.UserRole
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

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
    var roles: MutableList<UserRole> = mutableListOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "ps_user_cart",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "product_base_id", referencedColumnName = "id")]
    )
    var productsInCart: MutableSet<ProductBase> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()
}
