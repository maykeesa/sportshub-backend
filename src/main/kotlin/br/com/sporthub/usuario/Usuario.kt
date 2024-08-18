package br.com.sporthub.usuario

import jakarta.persistence.*
import java.time.LocalDateTime
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
    var dataNascimento: LocalDateTime,
    var genero: String,
    var telefone: String
) {
}