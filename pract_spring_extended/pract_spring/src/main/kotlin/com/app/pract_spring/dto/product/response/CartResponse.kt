package com.app.pract_spring.dto.product.response

data class CartResponse(
    var userId: Long,
    var product: ProductResponse
)
