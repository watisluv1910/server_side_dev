package com.app.pract_spring.dto.product.request

data class CreateProductRequest(
    var name: String,
    var costUSD: Long,
    var typeName: String,
    var sellerId: Long
)