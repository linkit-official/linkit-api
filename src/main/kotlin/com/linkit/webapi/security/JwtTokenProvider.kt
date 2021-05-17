package com.linkit.webapi.security

import com.linkit.core.exception.InvalidTokenException
import com.linkit.domain.user.model.Role
import com.linkit.webapi.commons.dto.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${jwt.access.key}") private val accessSecretKey: String,
    @Value("\${jwt.access.valid-seconds}") private val accessValidSeconds: Int,
    @Value("\${jwt.refresh.key}") private val refreshSecretKey: String,
    @Value("\${jwt.refresh.valid-seconds}") private val refreshValidSeconds: Int
) : TokenProvider {
    val accessTokenValidMillisecond = accessValidSeconds * 1000L
    val refreshTokenValidMillisecond = refreshValidSeconds * 1000L


    override fun createToken(userId: Long, username: String, roles: Set<Role>): Token {
        return Token(
            accessToken = generatedJwt(userId, username, roles, accessSecretKey, accessTokenValidMillisecond),
            refreshToken = generatedJwt(userId, username, roles, refreshSecretKey, refreshTokenValidMillisecond),
            BEARER_TYPE
        )
    }

    override fun refreshToken(refreshToken: String): Token {
        if (!validateToken(refreshToken, refreshSecretKey)) {
            throw InvalidTokenException("Invalid sign token")
        }
        val claims = getClaims(refreshToken, refreshSecretKey)
        return createToken(
            claims.subject.toLong(),
            claims[AUTHORITIES_USERNAME].toString(),
            claims[AUTHORITIES_ROLES].toString().split(",").map { Role.valueOf(it) }.toSet(),
        )
    }

    override fun validateAccessToken(accessToken: String): Boolean {
        return validateToken(accessToken, accessSecretKey)
    }

    override fun removePrefix(token: String?): String {
        token?.let {
            if (!token.startsWith(BEARER_TYPE)) {
                throw InvalidTokenException("Token type error")
            }
            return token.substring(BEARER_TYPE.length).trim()
        } ?: throw InvalidTokenException("Token is null")
    }

    override fun getAuthentication(accessToken: String): Authentication {
        val claims = getClaims(accessToken, accessSecretKey)
        return UserSession(
            userId = claims.subject.toLong(),
            username = claims[AUTHORITIES_USERNAME].toString(),
            roles = claims[AUTHORITIES_ROLES].toString().split(",").map { Role.valueOf(it) }.toSet(),
        )
    }

    private fun getClaims(jwtToken: String, secretKey: String): Claims {
        return try {
            Jwts.parser().setSigningKey(generateKey(secretKey)).parseClaimsJws(jwtToken).body
        } catch (e: Exception) {
            throw InvalidTokenException(e.message)
        }
    }

    private fun validateToken(jwtToken: String, secretKey: String): Boolean {
        return try {
            val claims = getClaims(jwtToken, secretKey)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    private fun generatedJwt(
        userId: Long,
        username: String,
        roles: Set<Role>,
        secretKey: String,
        validMillisecond: Long
    ): String {
        val claims = Jwts.claims().setSubject(userId.toString())
        claims[AUTHORITIES_USERNAME] = username
        claims[AUTHORITIES_ROLES] = roles.joinToString(",") { it.name }
        val now = Date()
        return Jwts.builder()
            .setClaims(claims) // 데이터
            .setIssuedAt(now) // 토큰 발행일자
            .setExpiration(Date(now.time + validMillisecond)) // set Expire Time
            .signWith(SignatureAlgorithm.HS256, generateKey(secretKey)) // 암호화 알고리즘, secret값 세팅
            .compact()
    }

    companion object {
        private const val AUTHORITIES_USERNAME = "username"
        private const val AUTHORITIES_ROLES = "roles"
        private const val BEARER_TYPE = "Bearer"
        private fun generateKey(secretKey: String): ByteArray = secretKey.toByteArray(StandardCharsets.UTF_8)
    }
}
