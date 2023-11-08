package com.app.pract_spring_auth.security.config

import com.app.pract_spring_auth.security.token.TokenUtils
import com.app.pract_spring_auth.service.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    val tokenUtils: TokenUtils,
    val userDetailsService: UserDetailsServiceImpl
): OncePerRequestFilter() {

    /**
     * Same contract as for `doFilter`, but guaranteed to be
     * just invoked once per request within a single request thread.
     *
     * Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token: String? = request.getParameter("t")

            token?.let {
                if (it.isNotEmpty()
                    && tokenUtils.isTokenValid(token)
                    && SecurityContextHolder.getContext().authentication == null) {

                    val userDetails = userDetailsService.loadUserByUsername(tokenUtils.extractUsername(token))

                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: ${e.message}")
        } finally {
            filterChain.doFilter(request, response)
        }
    }
}