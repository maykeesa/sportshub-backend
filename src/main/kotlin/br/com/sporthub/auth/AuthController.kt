package br.com.sporthub.auth

import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import com.example.auth.domain.user.UserRole
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
import java.time.LocalDateTime

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
    fun login(@RequestBody @Valid data: AuthForm): ResponseEntity<Any> {
        println(data.email)

        val usernamePassword = UsernamePasswordAuthenticationToken(data.email, data.senha)
        val auth = authManager.authenticate(usernamePassword)

        val token = tokenService.generateToken(auth.principal as Usuario)

        return ResponseEntity.ok(token)
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid data: RegisterDto): ResponseEntity<Any> {
        if (this.usuarioRepository.findByEmail(data.email) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado")
        }

        var encryptedPassword = BCryptPasswordEncoder().encode(data.senha)
        data.senha = encryptedPassword

        val mapper = UtilsService.getGenericModelMapper()
        val usuario: Usuario = mapper.map(data, Usuario::class.java)
        usuario.role = UserRole.USER

        val saveUsuario: Usuario = this.usuarioRepository.save(usuario)

        usuarioRepository.save(usuario)

        return ResponseEntity.ok("Usuário registrado com sucesso")
    }

    @PostMapping("/registerAdmin")
    fun registerAdmin(@RequestBody @Valid data: RegisterDto): ResponseEntity<Any> {
        if (this.usuarioRepository.findByEmail(data.email) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado")
        }

        var encryptedPassword = BCryptPasswordEncoder().encode(data.senha)
        data.senha = encryptedPassword

        val mapper = UtilsService.getGenericModelMapper()
        val usuario: Usuario = mapper.map(data, Usuario::class.java)
        usuario.role = UserRole.ADMIN

        val saveUsuario: Usuario = this.usuarioRepository.save(usuario)

        usuarioRepository.save(usuario)

        return ResponseEntity.ok("Usuário registrado com sucesso")
    }

}