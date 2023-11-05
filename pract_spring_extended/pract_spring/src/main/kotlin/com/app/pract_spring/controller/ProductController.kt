package com.app.pract_spring.controller

import com.app.pract_spring.dto.product.request.CreateProductRequest
import com.app.pract_spring.dto.product.request.UpdateProductRequest
import com.app.pract_spring.dto.product.response.ProductResponse
import com.app.pract_spring.model.product.ProductBase
import com.app.pract_spring.service.ProductService
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
@RequestMapping("/api/v1/products")
class ProductController(
    val productService: ProductService
) {

    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ResponseEntity<*> =
        ResponseEntity<ProductResponse>(
            productService.createProduct(request).toResponse(),
            HttpStatus.CREATED
        )

    @GetMapping
    fun getAllProducts(): ResponseEntity<*> =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProducts().map { it.toResponse() },
            HttpStatus.OK
        )

    @GetMapping("/{sellerId}")
    fun getProductBySellerId(@PathVariable("sellerId") sellerId: Long) : ResponseEntity<*> =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProductsBySellerId(sellerId).map { it.toResponse() },
            HttpStatus.OK
        )

    @GetMapping("/{type}")
    fun getProductsByType(@PathVariable("type") typeName: String): ResponseEntity<*> =
        ResponseEntity<List<ProductResponse>>(
            productService.findAllProductsByTypeName(typeName).map { it.toResponse() },
            HttpStatus.OK
        )

    @PutMapping
    fun updateProduct(@RequestBody request: UpdateProductRequest): ResponseEntity<*> =
        ResponseEntity<ProductResponse>(
            productService.updateProduct(request).toResponse(),
            HttpStatus.OK
        )

    @DeleteMapping
    fun deleteProductById(@RequestParam(name = "productId") toDeleteId: Long): ResponseEntity<*> =
        ResponseEntity<ProductResponse>(
            productService.deleteProduct(toDeleteId).toResponse(),
            HttpStatus.OK
        )
}