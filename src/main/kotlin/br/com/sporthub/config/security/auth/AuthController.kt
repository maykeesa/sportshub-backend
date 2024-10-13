package br.com.sporthub.config.security.auth

import br.com.sporthub.config.security.TokenService
import br.com.sporthub.config.security.auth.form.AuthForm
import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import br.com.sporthub.usuario.UserRole
import br.com.sporthub.usuario.dto.UsuarioDto
import br.com.sporthub.usuario.form.UsuarioForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    private lateinit var usuarioRepository : UsuarioRepository

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/login")
    @Operation(summary = "Realizar login")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    ])
    fun login(@RequestBody @Valid authForm: AuthForm): ResponseEntity<Any> {
        val usernamePassword = UsernamePasswordAuthenticationToken(authForm.email, authForm.senha)
        val auth = authManager.authenticate(usernamePassword)
        val token = tokenService.generateToken(auth.principal as Usuario)

        val usuario = auth.principal as Usuario

        return ResponseEntity.ok(mapOf("token" to token, "usuario" to UsuarioDto(usuario)))
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        ApiResponse(responseCode = "422", description = "Email já cadastrado")
    ])
    fun register(@RequestBody @Valid usuarioForm: UsuarioForm): ResponseEntity<Any> {
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

    @PostMapping("/registerAdmin")
    @Operation(summary = "Registrar um administrador")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Administrador registrado com sucesso"),
        ApiResponse(responseCode = "422", description = "Email já cadastrado")
    ])
    fun registerAdmin(@RequestBody @Valid usuarioForm: UsuarioForm): ResponseEntity<Any> {
        val usuarioOpt = this.usuarioRepository.findByEmail(usuarioForm.email)

        if (usuarioOpt.isPresent) {
            return ResponseEntity.status(422).body(mapOf("warn" to "Email já cadastrado."))
        }

        val encryptedPassword = BCryptPasswordEncoder().encode(usuarioForm.senha)
        usuarioForm.senha = encryptedPassword

        val mapper = UtilsService.getGenericModelMapper()
        val usuario: Usuario = mapper.map(usuarioForm, Usuario::class.java)
        usuario.role = UserRole.ADMIN

        val usuarioPersistido: Usuario = this.usuarioRepository.save(usuario)

        return ResponseEntity.status(201).body(UsuarioDto(usuarioPersistido))
    }

}