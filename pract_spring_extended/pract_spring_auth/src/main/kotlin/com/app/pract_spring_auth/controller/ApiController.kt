package com.app.pract_spring_auth.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api/v1")
class ApiController(
    val httpServletRequest: HttpServletRequest
) {

//    var authentication = SecurityContextHolder.getContext().authentication

    @Value("\${app.api.server}")
    val apiServer: String? = null

    @Value("\${app.api.port}")
    val apiServicePort: Int? = null

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/**")
    fun userEndpoints(@RequestBody(required = false) body: Any?) = makeRequest(body)

    @PreAuthorize("hasAuthority('SELLER')")
    @RequestMapping("/sellerboard/**")
    fun sellerEndpoints(@RequestBody(required = false) body: Any?) = makeRequest(body)

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/adminboard/**")
    fun adminEndpoints(@RequestBody(required = false) body: Any?) = makeRequest(body)

    private fun makeRequest(body: Any?) =
        RestTemplate().exchange(
            "http://$apiServer:$apiServicePort" + getFullRequestURI(httpServletRequest),
            HttpMethod.valueOf(httpServletRequest.method),
            body?.let { HttpEntity(body) },
            Any::class.java
//            mapOf("authorizedUserId" to authentication)
        )

    private fun getFullRequestURI(request: HttpServletRequest): String {
        val requestURL = request.requestURI
        val queryString = request.queryString

        return queryString?.let {
            "$requestURL?$queryString"
        } ?: requestURL
    }
}