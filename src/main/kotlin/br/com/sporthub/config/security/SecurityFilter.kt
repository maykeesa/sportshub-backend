package br.com.sporthub.config.security

import br.com.sporthub.usuario.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getToken(request)

        if (token.isNotEmpty()) {
            val email: String = tokenService.validateToken(token)

            val usuarioOpt = usuarioRepository.findByEmail(email)
            val user = if(usuarioOpt.isPresent) usuarioOpt.get() else null
            var authentication = UsernamePasswordAuthenticationToken(user, null, user?.authorities ?: listOf())
            SecurityContextHolder.getContext().authentication = authentication
        }

        println("chegou")
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String {
        val token: String? = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7, token.length)
        }

        return ""
    }
}