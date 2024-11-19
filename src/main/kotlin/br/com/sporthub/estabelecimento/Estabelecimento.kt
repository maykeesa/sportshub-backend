package br.com.sporthub.estabelecimento

import br.com.sporthub.quadra.Quadra
import br.com.sporthub.usuario.enums.UserRole
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "estabelecimentos")
data class Estabelecimento(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var email: String,
    var senha: String,
    var role: UserRole,
    var cnpj: String,
    var contato: String,
    var endereco: String,
    var cep: String,
    var descricao: String,

    @OneToMany(mappedBy = "estabelecimento", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonBackReference
    val quadras: List<Quadra>,

    @CreationTimestamp
    var dataCriacao: LocalDateTime
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_ESTABLISHMENT"))
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
}
