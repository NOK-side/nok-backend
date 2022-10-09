package com.example.nokbackend.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

//const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 12
const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60
//const val ONE_WEEK_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 24 * 7
const val ONE_WEEK_IN_MILLISECONDS: Long = 1000 * 60 * 3

@Component
class JwtTokenProvider(
    private val signingKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256),
    private val accessTokenExpirationInMilliseconds: Long = TWELVE_HOURS_IN_MILLISECONDS,
    private val refreshTokenExpirationInMilliseconds: Long = ONE_WEEK_IN_MILLISECONDS,
    private val claimEmail: String = "email"
) {
    fun createAccessToken(payload: String): String {
        val now = Date()
        val expiration = Date(now.time + accessTokenExpirationInMilliseconds)
        return Jwts.builder()
            .claim(claimEmail, payload)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()
    }

    fun createRefreshToken(payload: String): String {
        val now = Date()
        val expiration = Date(now.time + refreshTokenExpirationInMilliseconds)
        return Jwts.builder()
            .claim(claimEmail, payload)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()
    }

    fun getEmail(token: String): String {
        if (isValidToken(token).not()) {
            throw RuntimeException("유효한 토큰이 아닙니다")
        }

        return getClaimsJws(token).body[claimEmail]
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