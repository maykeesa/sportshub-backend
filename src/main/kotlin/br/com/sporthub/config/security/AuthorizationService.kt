package br.com.sporthub.config.security

import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AuthorizationService: UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val usuarioOpt: Optional<Usuario> = usuarioRepository.findByEmail(email)

        if (usuarioOpt.isPresent) {
            return usuarioOpt.get()
        }

        throw UsernameNotFoundException("Usuário não encontrado.")
    }
}