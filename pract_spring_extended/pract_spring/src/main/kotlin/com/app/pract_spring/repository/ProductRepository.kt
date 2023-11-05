package com.app.pract_spring.repository

import com.app.pract_spring.model.product.ProductBase
import com.app.pract_spring.model.product.ProductType
import com.app.pract_spring.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductBase, Long> {

    fun findAllBySeller(seller: User): List<ProductBase>

    fun findAllByTypeOrderByNameAsc(type: ProductType): List<ProductBase>
}