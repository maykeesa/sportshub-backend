package br.com.sporthub.auth

import br.com.sporthub.usuario.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service

class AuthorizationService: UserDetailsService {
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(email)
        if (usuario != null) {
            return usuario
        } else {
            throw UsernameNotFoundException("Usuário não encontrado.")
        }
    }
}