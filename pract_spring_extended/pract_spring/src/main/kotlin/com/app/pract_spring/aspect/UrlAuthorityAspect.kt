package com.app.pract_spring.aspect

import com.app.pract_spring.controller.ProductController
import com.app.pract_spring.controller.UserController
import com.app.pract_spring.model.user.role.EUserRole
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Aspect
@Component
class UrlAuthorityAspect {

    @Around("execution(* com.app.pract_spring.controller.ProductController.*(..))" +
            "|| execution(* com.app.pract_spring.controller.UserCartController.*(..)) " +
            "|| execution(* com.app.pract_spring.controller.UserController.*(..))")
    fun checkUrlAuthority(joinPoint: ProceedingJoinPoint): Any {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val requestURL = request.requestURL.toString()
        val boardRegex = ".*(admin|seller)board.*".toRegex()

        boardRegex.find(requestURL)?.let { matchResult ->
            val methodName: String = joinPoint.signature.name
            val authority: String = matchResult.groups[1]!!.value.uppercase()
            checkAuthority(authority, methodName)?.let {
                if (!it) throw Exception("The $authority doesn't have rights to access method $methodName.")
            }
        } ?: {
            if ("board" in requestURL) throw IllegalArgumentException("The provided board name is not valid.")
        }

        return joinPoint.proceed()
    }

    private companion object {
        fun getMethodsByRoleMap(): Map<EUserRole, List<String>> {
            val roleToMethods = mutableMapOf<EUserRole, List<String>>()

            roleToMethods[EUserRole.SELLER] = listOf(
                ProductController::updateProduct,
                ProductController::deleteProductById,
                ProductController::createProduct
            ).map { it.name }

            roleToMethods[EUserRole.ADMIN] = listOf(
                ProductController::deleteProductById,
                UserController::getAllUsers,
                UserController::getUserById
            ).map { it.name }

            return roleToMethods
        }

        fun checkAuthority(roleName: String, methodName: String): Boolean? {
            if (roleName in EUserRole.values().map { it.name }) {
                val isMappedByRole = getMethodsByRoleMap()[EUserRole.valueOf(roleName)]?.contains(methodName)
                return if (isMappedByRole == false) {
                    methodName !in getMethodsByRoleMap().values.flatten()
                } else
                    isMappedByRole
            }
            else
                throw IllegalArgumentException("The provided role $roleName is not valid.")
        }
    }

}