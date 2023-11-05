package com.app.pract_spring.controller

import com.app.pract_spring.dto.product.response.CartResponse
import com.app.pract_spring.dto.product.response.ProductResponse
import com.app.pract_spring.service.UserCartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users/{id}/cart")
class UserCartController(
    val cartService: UserCartService
) {

    @PostMapping
    fun addProductToCart(
        @PathVariable(name = "id") userId: Long,
        @RequestParam(name = "productId") productId: Long
    ) =
        ResponseEntity<CartResponse>(
            cartService.addProductToCart(userId, productId),
            HttpStatus.CREATED
        )

    @GetMapping
    fun getProductsFromCart(@PathVariable(name = "id") userId: Long) =
        ResponseEntity<List<ProductResponse>>(
            cartService.findProductsInCartByUserId(userId),
            HttpStatus.OK
        )

    @DeleteMapping("/delete")
    fun deleteProductFromCart(
        @PathVariable(name = "id") userId: Long,
        @RequestParam(name = "productId") productId: Long
    ) =
        ResponseEntity<CartResponse>(
            cartService.deleteProductFromCart(userId, productId),
            HttpStatus.OK
        )

    @DeleteMapping
    fun clearCart(@PathVariable(name = "id") userId: Long) =
        ResponseEntity<List<CartResponse>>(
            cartService.clearUserCart(userId),
            HttpStatus.OK
        )
}
