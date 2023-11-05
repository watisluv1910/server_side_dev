package com.app.pract_spring.service

import com.app.pract_spring.dto.product.request.CreateProductRequest
import com.app.pract_spring.dto.product.request.UpdateProductRequest
import com.app.pract_spring.model.product.ProductBase
import com.app.pract_spring.model.product.ProductType
import com.app.pract_spring.repository.ProductRepository
import com.app.pract_spring.repository.ProductTypeRepository
import com.app.pract_spring.repository.UserRepository
import org.springframework.data.domain.Example
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.NoSuchElementException

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val productTypeRepository: ProductTypeRepository
) {
    fun createProduct(request: CreateProductRequest): ProductBase {
        val foundProductType = productTypeRepository.findByName(request.typeName)

        return productRepository.save(
            ProductBase().apply {
                name = request.name
                costUSD = request.costUSD
                seller = userRepository.findById(request.sellerId).orElseThrow {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "The specified user does not exist.")
                }
                if (foundProductType.isEmpty)
                    type = productTypeRepository.save(
                        ProductType().also {
                            type -> type.name = request.typeName
                        }
                    )
                else
                    type = foundProductType.get()
            }
        )
    }

    fun updateProduct(request: UpdateProductRequest): ProductBase {
        val product = productRepository.findById(request.productId).orElseThrow()

        product.apply {
            name = request.name
            costUSD = request.costUSD
            seller = userRepository.findById(request.sellerId).orElseThrow()
            type = productTypeRepository.findByName(request.typeName).orElseThrow()
        }

        return productRepository.save(product)
    }

    fun findProductById(productId: Long): ProductBase {
        return productRepository.findById(productId).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The specified product does not exist.")
        }
    }

    fun findAllProducts(): List<ProductBase> = productRepository.findAll()

    fun findAllProductsBySellerId(sellerId: Long): List<ProductBase> {
        val seller = userRepository.findById(sellerId).orElseThrow()
        return productRepository.findAllBySeller(seller)
    }

    fun findAllProductsByTypeName(typeName: String): List<ProductBase> {
        val foundType = productTypeRepository.findByName(typeName)
            .orElseThrow {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "The specified type does not exist.")
            }
        return productRepository.findAllByTypeOrderByNameAsc(foundType)
    }

    fun deleteProduct(id: Long): ProductBase {
        val foundProduct = productRepository.findById(id);

        if (foundProduct.isPresent)
            productRepository.deleteById(id)
        else
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No such product in a database.")

        return foundProduct.get()
    }
}