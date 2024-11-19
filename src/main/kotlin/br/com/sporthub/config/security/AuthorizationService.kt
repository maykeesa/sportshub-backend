package br.com.sporthub.config.security

import br.com.sporthub.estabelecimento.EstabelecimentoRepository
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AuthorizationService: UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var estabelecimentoRepository: EstabelecimentoRepository

    override fun loadUserByUsername(email: String): UserDetails? {
        val usuarioOpt = usuarioRepository.findByEmail(email)
        if (usuarioOpt.isPresent) {
            return usuarioOpt.get()
        }

        val estabelecimentoOpt = estabelecimentoRepository.findByEmail(email)
        if (estabelecimentoOpt.isPresent) {
            return estabelecimentoOpt.get()
        }

        throw UsernameNotFoundException("Usuário ou Estabelecimento não encontrado.")
    }

    fun getUsuarioLogado(): Any {
        val principal = SecurityContextHolder.getContext().authentication.principal

        if (principal is UserDetails) {
            return principal as Usuario
        }

        return principal.toString()
    }
}