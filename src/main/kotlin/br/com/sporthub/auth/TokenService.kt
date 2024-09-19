package br.com.sporthub.auth
import br.com.sporthub.usuario.Usuario
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService {

    private val secret: String = "O Shadow cara, o Shadow véi, O Shadow cara, o Shadow véi , O Shadow cara, o Shadow véi... O Shadow... WOOOOOW"

    fun generateToken(usuario: Usuario): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secret)
            return com.auth0.jwt.JWT.create()
                .withIssuer("auth0")
                .withClaim("id", usuario.id.toString())
                .withClaim("email", usuario.email)
                .withClaim("nome", usuario.nome)
                .sign(algorithm)
        } catch (e: Exception) {
            throw Exception("Erro ao gerar token")
        }
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
            return ""
        }
    }

    private fun getExpirationTime(): Instant = Instant.now().plusSeconds(3600)

}