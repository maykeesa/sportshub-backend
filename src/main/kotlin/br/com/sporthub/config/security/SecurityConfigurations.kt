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
    private lateinit var securityfilter: SecurityFilter

    @Bean
    fun securityFilterChain(http: HttpSecurity, jogoService: JogoService, horarioService: HorarioService): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/doc/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/webjars/**").permitAll()

                    // Mapeamento rotas auth
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/registerAdmin").permitAll()

                    // Mapeamento rotas de esporte
//                    .requestMatchers(HttpMethod.GET, "/esporte").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/esporte/**").hasAnyRole()
                    .requestMatchers(HttpMethod.POST, "/esporte").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/esporte/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/esporte/**").hasRole("ADMIN")

                    // Mapeamento rotas de estabelecimento
//                    .requestMatchers(HttpMethod.GET, "/estabelecimento").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/estabelecimento/**").hasAnyRole()
                    .requestMatchers(HttpMethod.POST, "/estabelecimento").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/estabelecimento/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/estabelecimento/**").hasRole("ADMIN")

                    // Mapeamento rotas de grupo
//                    .requestMatchers(HttpMethod.GET, "/grupo").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/grupo/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/grupo").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/grupo/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/grupo/**").hasAnyRole()

                    // Mapeamento rotas de quadra
//                    .requestMatchers(HttpMethod.GET, "/quadra").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/quadra/**").hasAnyRole()
                    .requestMatchers(HttpMethod.POST, "/quadra").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/quadra/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/quadra/**").hasRole("ADMIN")

                    // Mapeamento rotas de usuario
//                    .requestMatchers(HttpMethod.GET, "/usuario").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/usuario").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAnyRole()

                    // Mapeamento rotas de reserva
//                    .requestMatchers(HttpMethod.GET, "/reserva").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/reserva/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/reserva").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/reserva/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/reserva/**").hasAnyRole()

                    // Mapeamento rotas de torneio
//                    .requestMatchers(HttpMethod.GET, "/torneio").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/torneio/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/torneio").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/torneio/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/torneio/**").hasAnyRole()

                    // Mapeamento rotas de jogo
//                    .requestMatchers(HttpMethod.GET, "/jogo").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/jogo/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/jogo").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/jogo/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/jogo/**").hasAnyRole()

                    // Mapeamento horario
//                    .requestMatchers(HttpMethod.GET, "/horario").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/horario/**").hasAnyRole()
                    .requestMatchers(HttpMethod.POST, "/horario").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/horario/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/horario/**").hasRole("ADMIN")

                    // Mapeamento rotas de estatistica
//                    .requestMatchers(HttpMethod.GET, "/estatistica").hasAnyRole()
//                    .requestMatchers(HttpMethod.GET, "/estatistica/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.POST, "/estatistica").hasAnyRole()
//                    .requestMatchers(HttpMethod.PUT, "/estatistica/**").hasAnyRole()
//                    .requestMatchers(HttpMethod.DELETE, "/estatistica/**").hasAnyRole()
                    .anyRequest()
                    .authenticated()
            }
            .addFilterBefore(securityfilter, UsernamePasswordAuthenticationFilter::class.java)
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
