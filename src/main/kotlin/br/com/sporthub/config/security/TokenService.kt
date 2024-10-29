package br.com.sporthub.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService {

    @Value("\$sportshub.security.secret}")
    private lateinit var secret: String

    fun generateToken(user: UserDetails): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        val email = user.username
        
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("email", email)
            .sign(algorithm)
    }

    fun validateToken(token: String): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secret)

            return JWT.require(algorithm)
                .withIssuer("auth0")
                .build()
                .verify(token)
                .getClaim("email").asString()

        } catch (e: Exception) {
            throw Exception("Erro ao validar o token.")
        }
    }

    private fun getExpirationTime(): Instant = Instant.now().plusSeconds(3600)

}