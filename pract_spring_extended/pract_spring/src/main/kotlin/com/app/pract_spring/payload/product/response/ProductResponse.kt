package com.app.pract_spring.payload.product.response

data class ProductResponse(
    var id: Long,
    var name: String,
    var costUSD: Long,
    var typeName: String,
    var sellerUsername: String,
    var sellerDisplayName: String
)
