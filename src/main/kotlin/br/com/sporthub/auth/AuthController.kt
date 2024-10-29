package br.com.sporthub.auth

import br.com.sporthub.config.security.TokenService
import br.com.sporthub.auth.form.AuthForm
import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.estabelecimento.EstabelecimentoRepository
import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import br.com.sporthub.usuario.UserRole
import br.com.sporthub.usuario.dto.UsuarioDto
import br.com.sporthub.usuario.form.UsuarioForm
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authManager: AuthenticationManager
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var estabelecimentoRepository: EstabelecimentoRepository
    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/login/usuario")
    fun loginUsuario(@RequestBody @Valid authForm: AuthForm): ResponseEntity<Any> {
        val usernamePassword = UsernamePasswordAuthenticationToken(authForm.email, authForm.senha)
        val auth = authManager.authenticate(usernamePassword)
        val usuario = auth.principal as Usuario
        val token = tokenService.generateToken(usuario)

        return ResponseEntity.ok(mapOf("token" to token, "usuario" to UsuarioDto(usuario)))
    }

    @PostMapping("/login/estabelecimento")
    fun loginEstabelecimento(@RequestBody @Valid authForm: AuthForm): ResponseEntity<Any> {
        val usernamePassword = UsernamePasswordAuthenticationToken(authForm.email, authForm.senha)
        val auth = authManager.authenticate(usernamePassword)
        val estabelecimento = auth.principal as Estabelecimento
        val token = tokenService.generateToken(estabelecimento)

        return ResponseEntity.ok(mapOf("token" to token, "estabelecimento" to EstabelecimentoDto(estabelecimento)))
    }

    @PostMapping("/register/usuario")
    fun registerUsuario(@RequestBody @Valid usuarioForm: UsuarioForm): ResponseEntity<Any> {
        val usuarioOpt: Optional<Usuario> = this.usuarioRepository.findByEmail(usuarioForm.email)

        if (usuarioOpt.isPresent) {
            return ResponseEntity.status(422).body(mapOf("warn" to "Email já cadastrado."))
        }

        val encryptedPassword = BCryptPasswordEncoder().encode(usuarioForm.senha)
        usuarioForm.senha = encryptedPassword

        val mapper = UtilsService.getGenericModelMapper()
        val usuario: Usuario = mapper.map(usuarioForm, Usuario::class.java)
        usuario.role = UserRole.USER

        val usuarioPersistido: Usuario = this.usuarioRepository.save(usuario)

        return ResponseEntity.status(201).body(UsuarioDto(usuarioPersistido))
    }

    @PostMapping("/register/estabelecimento")
    fun registerEstabelecimento(@RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<Any> {
        val estabelecimentoOpt = this.estabelecimentoRepository.findByEmail(estabelecimentoForm.email)

        if (estabelecimentoOpt.isPresent) {
            return ResponseEntity.status(422).body(mapOf("warn" to "Email já cadastrado."))
        }

        val encryptedPassword = BCryptPasswordEncoder().encode(estabelecimentoForm.senha)
        estabelecimentoForm.senha = encryptedPassword

        val mapper = UtilsService.getGenericModelMapper()
        val estabelecimento: Estabelecimento = mapper.map(estabelecimentoForm, Estabelecimento::class.java)
        estabelecimento.role = UserRole.ESTABLISHMENT

        val estabelecimentoPersistido: Estabelecimento = this.estabelecimentoRepository.save(estabelecimento)

        return ResponseEntity.status(201).body(EstabelecimentoDto(estabelecimentoPersistido))
    }
}
