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
                    // Rotas públicas
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/doc/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/webjars/**").permitAll()

                    // Rotas de autenticação e registro
                    .requestMatchers(HttpMethod.POST, "/auth/login/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()

                    // Rotas de esporte (estabelecimentos podem criar/modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/esporte/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/esporte").hasAnyRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/esporte/**").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/esporte/**").hasRole("ESTABLISHMENT")

                    // Rotas de estabelecimento (apenas estabelecimentos podem modificar suas informações)
                    .requestMatchers(HttpMethod.GET, "/estabelecimento/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/estabelecimento").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/estabelecimento/**").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/estabelecimento/**").hasRole("ESTABLISHMENT")

                    // Rotas de grupo (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/grupo").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.GET, "/grupo/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.POST, "/grupo").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/grupo/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/grupo/**").hasAnyRole("USER", "ESTABLISHMENT")

                    // Rotas de quadra (somente estabelecimentos podem modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/quadra").permitAll()
                    .requestMatchers(HttpMethod.GET, "/quadra/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/quadra").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/quadra/**").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/quadra/**").hasRole("ESTABLISHMENT")

                    // Rotas de usuario (Usuários e Estabelecimentos podem ver e modificar seus próprios perfis)
                    .requestMatchers(HttpMethod.GET, "/usuario").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAnyRole("USER", "ESTABLISHMENT")

                    // Rotas de reserva (acesso público para criar reservas, estabelecimentos podem editar)
                    .requestMatchers(HttpMethod.GET, "/reserva").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.GET, "/reserva/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.POST, "/reserva").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/reserva/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/reserva/**").hasAnyRole("USER", "ESTABLISHMENT")

                    // Rotas de torneio (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/torneio").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.GET, "/torneio/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.POST, "/torneio").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/torneio/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/torneio/**").hasAnyRole("USER", "ESTABLISHMENT")

                    // Rotas de jogo (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/jogo").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.GET, "/jogo/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.POST, "/jogo").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/jogo/**").hasAnyRole("USER", "ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/jogo/**").hasAnyRole("USER", "ESTABLISHMENT")

                    // Rotas de horário (apenas estabelecimentos podem modificar, usuários podem visualizar)
                    .requestMatchers(HttpMethod.GET, "/horario").permitAll()
                    .requestMatchers(HttpMethod.GET, "/horario/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/horario").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.PUT, "/horario/**").hasRole("ESTABLISHMENT")
                    .requestMatchers(HttpMethod.DELETE, "/horario/**").hasRole("ESTABLISHMENT")

                    // Rotas de estatística (usuários e estabelecimentos podem acessar e modificar)
                    .requestMatchers(HttpMethod.GET, "/estatistica").hasAnyRole("USER")
                    .requestMatchers(HttpMethod.GET, "/estatistica/**").hasAnyRole("USER")
                    .requestMatchers(HttpMethod.POST, "/estatistica").hasAnyRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/estatistica/**").hasAnyRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/estatistica/**").hasAnyRole("USER")

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
