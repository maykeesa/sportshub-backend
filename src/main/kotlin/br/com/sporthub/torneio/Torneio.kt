package br.com.sporthub.torneio

import br.com.sporthub.grupo.Grupo
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "torneios")
data class Torneio(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null, // Using nullable UUID to support default value

    var nome: String,

    var dataCriacao: LocalDateTime,

    var descricao: String,

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    var grupo: Grupo
)
