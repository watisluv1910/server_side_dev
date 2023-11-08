package com.app.pract_spring.model.product

import com.app.pract_spring.payload.product.response.ProductResponse
import com.app.pract_spring.model.user.User
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

@Entity
@Table(name = "ps_product_base", schema = "pract_spring_database")
class ProductBase {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "product_name", length = 255, nullable = false)
    var name: String? = null

    @Column(name = "product_cost_usd", nullable = false)
    var costUSD: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = false)
    lateinit var type: ProductType

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    lateinit var seller: User

    @ManyToMany(mappedBy = "productsInCart", fetch = FetchType.LAZY)
    var usersAddedTo: MutableSet<User> = mutableSetOf()

    @PreRemove
    fun removeProductFromCart() {
        for (user in usersAddedTo)
            user.productsInCart.remove(this)
    }

    fun toResponse(): ProductResponse =
        ProductResponse(
            id = this.id!!,
            name = this.name!!,
            costUSD = this.costUSD!!,
            typeName = this.type.name!!,
            sellerUsername = this.seller.username!!,
            sellerDisplayName = this.seller.displayName!!
        )

    fun isNew(): Boolean = id == null

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as ProductBase

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()
}
