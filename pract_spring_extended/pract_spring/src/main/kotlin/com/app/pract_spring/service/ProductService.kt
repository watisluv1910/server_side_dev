package com.app.pract_spring.service

import com.app.pract_spring.payload.product.request.CreateProductRequest
import com.app.pract_spring.payload.product.request.UpdateProductRequest
import com.app.pract_spring.model.product.ProductBase
import com.app.pract_spring.model.product.ProductType
import com.app.pract_spring.model.user.role.EUserRole
import com.app.pract_spring.repository.ProductRepository
import com.app.pract_spring.repository.ProductTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
    private val userService: UserService,
    private val productTypeRepository: ProductTypeRepository
) {
    fun createProduct(request: CreateProductRequest): ProductBase {
        val foundSeller = userService.findUserById(request.sellerId)

        if (!foundSeller.roles.map { it.roleName }.contains(EUserRole.SELLER)) {
            userService.addRoleToUser(foundSeller, EUserRole.SELLER.name)
        }

        return productRepository.save(
            ProductBase().apply {
                name = request.name
                costUSD = request.costUSD
                seller = foundSeller
                type = validateProductType(request.typeName)
            }
        )
    }

    fun updateProduct(request: UpdateProductRequest): ProductBase {
        val product = productRepository.findById(request.productId).orElseThrow()

        product.apply {
            name = request.name
            costUSD = request.costUSD
            seller = userService.findUserById(request.sellerId)
            type = validateProductType(request.typeName)
        }

        return productRepository.save(product)
    }

    private fun validateProductType(typeName: String): ProductType {
        val foundProductType = productTypeRepository.findByName(typeName)

        return if (foundProductType.isEmpty)
            productTypeRepository.save(ProductType().also { type -> type.name = typeName })
        else
            foundProductType.get()
    }

    fun findProductById(productId: Long): ProductBase {
        return productRepository.findById(productId).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The specified product does not exist.")
        }
    }

    fun findAllProducts(): List<ProductBase> = productRepository.findAll()

    fun findAllProductsBySellerId(sellerId: Long): List<ProductBase> {
        val seller = userService.findUserById(sellerId)
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