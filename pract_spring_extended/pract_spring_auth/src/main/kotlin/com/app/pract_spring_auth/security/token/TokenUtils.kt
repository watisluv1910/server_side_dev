package com.app.pract_spring_auth.security.token

import com.app.pract_spring_auth.service.UserDetailsImpl
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.Key
import java.util.Date

@Service
class TokenUtils(
    private val logger: Logger = LoggerFactory.getLogger(TokenUtils::class.java)
) {

    @Value("\${app.token.sign-key}")
    lateinit var SIGN_KEY: String

    @Value("\${app.token.expiration-time-ms}")
    lateinit var TOKEN_EXPIRATION_TIME_MS: BigInteger

    fun extractUsername(token: String?): String {
        return extractClaim(token) { claim: Claims? -> claim?.subject ?: "" }
    }

    fun <T> extractClaim(token: String?, claimsResolver: (Claims?) -> T): T {
        val claims = extractAllClaims(token!!)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SIGN_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(userDetails: UserDetailsImpl) = generateToken(mapOf(), userDetails)

    fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetailsImpl
    ): String = Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username!!)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MS.toLong()))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact()

    fun isTokenValid(token: String?): Boolean {
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}