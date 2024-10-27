package br.com.sporthub.config.security

import br.com.sporthub.estabelecimento.EstabelecimentoRepository
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
    @Autowired
    private lateinit var estabelecimentoRepository: EstabelecimentoRepository

    override fun loadUserByUsername(email: String): UserDetails? {
        // Tenta buscar no repositório de Usuários
        val usuarioOpt = usuarioRepository.findByEmail(email)
        if (usuarioOpt.isPresent) {
            return usuarioOpt.get() // Retorna os detalhes do Usuario
        }

        // Se não encontrou em Usuário, tenta buscar no repositório de Estabelecimentos
        val estabelecimentoOpt = estabelecimentoRepository.findByEmail(email)
        if (estabelecimentoOpt.isPresent) {
            return estabelecimentoOpt.get()
        }

        // Se não encontrou nenhum dos dois, lança exceção
        throw UsernameNotFoundException("Usuário ou Estabelecimento não encontrado.")
    }
}