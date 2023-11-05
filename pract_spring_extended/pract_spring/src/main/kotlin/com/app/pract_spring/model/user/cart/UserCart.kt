package com.app.pract_spring.model.user.cart

import com.app.pract_spring.model.product.ProductBase
import jakarta.persistence.*

@Entity
@IdClass(UserCartPK::class)
@Table(name = "ps_user_cart", schema = "pract_spring_database")
data class UserCart (

    @Id
    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Id
    @Column(name = "product_base_id", nullable = false)
    var productBaseId: Long
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "product_base_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false
    )
    lateinit var product: ProductBase
}
