package com.app.pract_spring.repository

import com.app.pract_spring.model.product.ProductType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductTypeRepository: JpaRepository<ProductType, Long> {

    fun findByName(typeName: String): Optional<ProductType>
}