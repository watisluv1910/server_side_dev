package com.app.pract_spring.controller

import com.app.pract_spring.payload.product.request.CreateProductRequest
import com.app.pract_spring.payload.product.request.UpdateProductRequest
import com.app.pract_spring.payload.product.response.ProductResponse
import com.app.pract_spring.service.ProductService
import org.aspectj.lang.annotation.Around
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [
    "/api/v1/products",
    "/api/v1/sellerboard/products",
    "/api/v1/adminboard/products"
])
class ProductController(
    val productService: ProductService
) {

    // By seller
    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest) =
        ResponseEntity<ProductResponse>(
            productService.createProduct(request).toResponse(),
            HttpStatus.CREATED
        )

    // By everyone
    @GetMapping
    fun getAllProducts() =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProducts().map { it.toResponse() },
            HttpStatus.OK
        )

    // By everyone
    @GetMapping("/{sellerId}")
    fun getProductBySellerId(@PathVariable("sellerId") sellerId: Long) =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProductsBySellerId(sellerId).map { it.toResponse() },
            HttpStatus.OK
        )

    // By everyone
    @GetMapping("/{type}")
    fun getProductsByType(@PathVariable("type") typeName: String) =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProductsByTypeName(typeName).map { it.toResponse() },
            HttpStatus.OK
        )

    // By seller
    // Add check if it's his product
    @PutMapping
    fun updateProduct(@RequestBody request: UpdateProductRequest) =
        ResponseEntity<ProductResponse>(
            productService.updateProduct(request).toResponse(),
            HttpStatus.OK
        )

    // By seller and admin
    // Add check if it's his product
    @DeleteMapping
    fun deleteProductById(@RequestParam(name = "productId") toDeleteId: Long) =
        ResponseEntity<ProductResponse>(
            productService.deleteProduct(toDeleteId).toResponse(),
            HttpStatus.OK
        )
}