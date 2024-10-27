package br.com.sporthub.config.security

import br.com.sporthub.horario.HorarioService
import br.com.sporthub.jogo.JogoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfigurations {

    @Autowired
    private lateinit var securityFilter: SecurityFilter

    @Bean
    fun securityFilterChain(http: HttpSecurity, jogoService: JogoService, horarioService: HorarioService): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize
                    // Rotas públicas (acesso irrestrito)
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/doc/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/webjars/**").permitAll()

                    // Rotas de autenticação e registro
                    .requestMatchers(HttpMethod.POST, "/auth/loginUsuario").permitAll() // Login para Usuários
                    .requestMatchers(HttpMethod.POST, "/auth/loginEstabelecimento").permitAll() // Login para Estabelecimentos
                    .requestMatchers(HttpMethod.POST, "/auth/registerUsuario").permitAll() // Registro para Usuários
                    .requestMatchers(HttpMethod.POST, "/auth/registerEstabelecimento").permitAll() // Registro para Estabelecimentos

                    // Rotas de esporte (estabelecimentos podem criar/modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/esporte/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/esporte").hasRole("ESTABELECIMENTO") // Somente Estabelecimentos podem criar
                    .requestMatchers(HttpMethod.PUT, "/esporte/**").hasRole("ESTABELECIMENTO") // Somente Estabelecimentos podem editar
                    .requestMatchers(HttpMethod.DELETE, "/esporte/**").hasRole("ESTABELECIMENTO") // Somente Estabelecimentos podem excluir

                    // Rotas de estabelecimento (apenas estabelecimentos podem modificar suas informações)
                    .requestMatchers(HttpMethod.GET, "/estabelecimento/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/estabelecimento").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/estabelecimento/**").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/estabelecimento/**").hasRole("ESTABELECIMENTO")

                    // Rotas de grupo (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/grupo").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/grupo/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/grupo").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/grupo/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/grupo/**").hasAnyRole("USER", "ESTABELECIMENTO")

                    // Rotas de quadra (somente estabelecimentos podem modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/quadra").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/quadra/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/quadra").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/quadra/**").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/quadra/**").hasRole("ESTABELECIMENTO")

                    // Rotas de usuario (Usuários e Estabelecimentos podem ver e modificar seus próprios perfis)
                    .requestMatchers(HttpMethod.GET, "/usuario").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/usuario").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAnyRole("USER", "ESTABELECIMENTO")

                    // Rotas de reserva (acesso público para criar reservas, estabelecimentos podem editar)
                    .requestMatchers(HttpMethod.GET, "/reserva").permitAll()
                    .requestMatchers(HttpMethod.GET, "/reserva/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/reserva").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/reserva/**").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/reserva/**").hasRole("ESTABELECIMENTO")

                    // Rotas de torneio (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/torneio").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/torneio/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/torneio").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/torneio/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/torneio/**").hasAnyRole("USER", "ESTABELECIMENTO")

                    // Rotas de jogo (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/jogo").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/jogo/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/jogo").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/jogo/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/jogo/**").hasAnyRole("USER", "ESTABELECIMENTO")

                    // Rotas de horário (apenas estabelecimentos podem modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/horario").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/horario/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/horario").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/horario/**").hasRole("ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/horario/**").hasRole("ESTABELECIMENTO")

                    // Rotas de estatística (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/estatistica").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.GET, "/estatistica/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.POST, "/estatistica").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.PUT, "/estatistica/**").hasAnyRole("USER", "ESTABELECIMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/estatistica/**").hasAnyRole("USER", "ESTABELECIMENTO")

                    // Qualquer outra requisição precisa estar autenticada
                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
