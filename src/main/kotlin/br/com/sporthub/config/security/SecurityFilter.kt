package br.com.sporthub.config.security

import br.com.sporthub.estabelecimento.EstabelecimentoRepository
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
    private lateinit var usuarioRep: UsuarioRepository

    @Autowired
    private lateinit var estabelecimentoRep: EstabelecimentoRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getToken(request)

        if (token.isNotEmpty()) {
            var user: Any?
            val email: String = tokenService.validateToken(token)

            var authentication: UsernamePasswordAuthenticationToken? = null

            if(this.usuarioRep.findByEmail(email).isPresent){
                user = this.usuarioRep.findByEmail(email).get()
                authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }

            if(this.estabelecimentoRep.findByEmail(email).isPresent){
                user = this.estabelecimentoRep.findByEmail(email).get()
                authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }

            SecurityContextHolder.getContext().authentication = authentication
        }

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