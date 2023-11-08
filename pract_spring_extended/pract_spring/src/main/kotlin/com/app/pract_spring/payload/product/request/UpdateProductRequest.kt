package com.app.pract_spring.payload.product.request

data class UpdateProductRequest(
    var productId: Long,
    var name: String,
    var costUSD: Long,
    var typeName: String,
    var sellerId: Long
)
