package com.example.nokbackend.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 12

@Component
class JwtTokenProvider(
    private val signingKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256),
    private val expirationInMilliseconds: Long = TWELVE_HOURS_IN_MILLISECONDS,
    private val CLAIM_EMAIL: String = "email"
) {
    fun createToken(payload: String): String {
        val now = Date()
        val expiration = Date(now.time + expirationInMilliseconds)
        return Jwts.builder()
            .claim(CLAIM_EMAIL, payload)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()
    }

    fun getEmail(token: String): String {
        if (isValidToken(token).not()) {
            throw RuntimeException("로그인 정보가 정확하지 않습니다")
        }

        return getClaimsJws(token).body[CLAIM_EMAIL]
            .toString()
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        } catch (e: ExpiredJwtException) {
            false
        }
    }

    private fun getClaimsJws(token: String): Jws<Claims> = Jwts.parserBuilder()
        .setSigningKey(signingKey.encoded)
        .build()
        .parseClaimsJws(token)
}