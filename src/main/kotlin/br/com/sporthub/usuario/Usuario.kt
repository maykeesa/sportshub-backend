
package br.com.sporthub.usuario
import br.com.sporthub.grupo.Grupo
import com.example.auth.domain.user.UserRole
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
    @CreationTimestamp
    var dataCriacao: LocalDateTime,
    var role: UserRole,

    @ManyToMany(mappedBy = "usuarios")
    @JsonBackReference
    var grupos: List<Grupo> = ArrayList()
) : UserDetails {

    // Implementação dos métodos obrigatórios da interface UserDetails
    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (this.role == UserRole.ADMIN) {
            return listOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        } else {
            return listOf(SimpleGrantedAuthority("ROLE_USER"))
        }
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true // Indica se a conta está expirada
    }

    override fun isAccountNonLocked(): Boolean {
        return true // Indica se a conta está bloqueada
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true // Indica se as credenciais (senha) estão expiradas
    }

    override fun isEnabled(): Boolean {
        return true // Indica se o usuário está ativo
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
