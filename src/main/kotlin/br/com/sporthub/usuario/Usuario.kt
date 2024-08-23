package br.com.sporthub.usuario

import br.com.sporthub.grupo.Grupo
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "usuarios")
data class Usuario(

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

    @ManyToMany(mappedBy = "usuario")
    var grupos: List<Grupo>
    ) {
}