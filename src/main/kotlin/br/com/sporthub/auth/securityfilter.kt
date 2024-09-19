package br.com.sporthub.auth

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
public class securityfilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = getToken(request)
        if (!token.isEmpty()) {
            val email = tokenService.validateToken(token)
            val user = usuarioRepository.findByEmail(email)
            var authentication = UsernamePasswordAuthenticationToken(user, null, user?.authorities ?: listOf())
            SecurityContextHolder.getContext().authentication = authentication

        }
        filterChain.doFilter(request, response)


    }

    private fun getToken(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")
        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            return ""
        }
        return token.substring(7, token.length)
    }
}