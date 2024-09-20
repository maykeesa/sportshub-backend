package br.com.sporthub.auth

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
    private lateinit var securityfilter: securityfilter


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
                    // mapeamento rotas auth
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/registerAdmin").hasRole("ADMIN")
                    // mapeamento rotas de esporte
                    .requestMatchers(HttpMethod.GET, "/esporte").permitAll()
                    .requestMatchers(HttpMethod.GET, "/esporte/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/esporte").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/esporte/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/esporte/**").hasRole("ADMIN")
                    // mapeamento rotas de estabelecimento
                    .requestMatchers(HttpMethod.GET, "/estabelecimento").permitAll()
                    .requestMatchers(HttpMethod.GET, "/estabelecimento/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/estabelecimento").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/estabelecimento/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/estabelecimento/**").hasRole("ADMIN")
                    // mapeamento rotas de grupo
                    .requestMatchers(HttpMethod.GET, "/grupo").permitAll()
                    .requestMatchers(HttpMethod.GET, "/grupo/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/grupo").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/grupo/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/grupo/**").hasRole("ADMIN")
                    // mapeamento rotas de quadra
                    .requestMatchers(HttpMethod.GET, "/quadra").permitAll()
                    .requestMatchers(HttpMethod.GET, "/quadra/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/quadra").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/quadra/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/quadra/**").hasRole("ADMIN")
                    // mapeamento rotas de usuario
                    .requestMatchers(HttpMethod.GET, "/usuario").permitAll()
                    .requestMatchers(HttpMethod.GET, "/usuario/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuario").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/usuario/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")
                    // mapeamento rotas de reserva
                    .requestMatchers(HttpMethod.GET, "/reserva").permitAll()
                    .requestMatchers(HttpMethod.GET, "/reserva/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/reserva").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/reserva/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/reserva/**").hasRole("ADMIN")
                    // mapeamento rotas de torneio
                    .requestMatchers(HttpMethod.GET, "/torneio").permitAll()
                    .requestMatchers(HttpMethod.GET, "/torneio/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/torneio").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/torneio/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/torneio/**").hasRole("ADMIN")
                    // mapeamento rotas de jogo
                    .requestMatchers(HttpMethod.GET, "/jogo").permitAll()
                    .requestMatchers(HttpMethod.GET, "/jogo/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/jogo").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/jogo/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/jogo/**").hasRole("ADMIN")
                    // mapeamento horario
                    .requestMatchers(HttpMethod.GET, "/horario").permitAll()
                    .requestMatchers(HttpMethod.GET, "/horario/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/horario").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/horario/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/horario/**").hasRole("ADMIN")
                    // mapeamento rotas de estatistica
                    .requestMatchers(HttpMethod.GET, "/estatistica").permitAll()
                    .requestMatchers(HttpMethod.GET, "/estatistica/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/estatistica").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/estatistica/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/estatistica/**").hasRole("ADMIN")
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
    public fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
