package br.com.sporthub.usuario

import br.com.sporthub.grupo.Grupo
import br.com.sporthub.usuario.enums.UserRole
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "usuarios")
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var cpf: String,
    var nome: String,
    var email: String,
    var senha: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
    var role: UserRole,

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
    @JsonBackReference
    var grupos: List<Grupo> = ArrayList(),

    @CreationTimestamp
    var dataCriacao: LocalDateTime

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun toString(): String {
        return "Usuario(id=$id, " +
                "cpf='$cpf', " +
                "nome='$nome', " +
                "email='$email', " +
                "senha='$senha', " +
                "dataNascimento=$dataNascimento, " +
                "genero='$genero', " +
                "telefone='$telefone', " +
                "dataCriacao=$dataCriacao, " +
                "grupos=${grupos.map { it.id }})"
    }
}
