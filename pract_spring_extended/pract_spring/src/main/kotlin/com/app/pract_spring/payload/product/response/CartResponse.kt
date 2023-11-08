package com.app.pract_spring.payload.product.response

data class CartResponse(
    var userId: Long,
    var product: ProductResponse
)
