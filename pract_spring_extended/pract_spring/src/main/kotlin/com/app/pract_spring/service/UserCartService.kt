package com.app.pract_spring.service

import com.app.pract_spring.payload.product.response.CartResponse
import com.app.pract_spring.payload.product.response.ProductResponse
import com.app.pract_spring.model.user.cart.UserCart
import com.app.pract_spring.model.user.cart.UserCartPK
import com.app.pract_spring.repository.UserCartRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class UserCartService(
    private val userService: UserService,
    private val productService: ProductService,
    private val cartRepository: UserCartRepository
) {

    fun addProductToCart(userId: Long, productId: Long): CartResponse {
        val user = userService.findUserById(userId)
        val product = productService.findProductById(productId)

        if (cartRepository.existsById(UserCartPK(userId, productId))) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Error: The product ${product.name} is already in the cart of the user ${user.username}."
            )
        }

        return cartRepository.save(
            UserCart(user.id!!, product.id!!).apply { this.product = product }
        ).toResponse()
    }

    fun deleteProductFromCart(userId: Long, productId: Long): CartResponse {
        val user = userService.findUserById(userId)
        val product = productService.findProductById(productId)

        val foundProduct = cartRepository.findById(UserCartPK(userId, productId))

        if (foundProduct.isEmpty)
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "The product ${product.name} is not in the cart of the user ${user.username}"
            )

        cartRepository.deleteById(UserCartPK(userId, productId))

        return foundProduct.get().toResponse()
    }

    fun findProductsInCartByUserId(userId: Long): List<ProductResponse> {
        val user = userService.findUserById(userId)
        return user.productsInCart.map { it.toResponse() }
    }

    fun clearUserCart(userId: Long): List<CartResponse> {
        val user = userService.findUserById(userId)

        for (product in user.productsInCart) {
            deleteProductFromCart(userId, product.id!!)
        }

        return cartRepository.deleteAllByUserId(userId).map { it.toResponse() }
    }

    fun UserCart.toResponse(): CartResponse =
        CartResponse(
            userId = this.userId,
            product = product.toResponse()
        )
}
