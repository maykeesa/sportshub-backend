package br.com.sporthub.torneio

import br.com.sporthub.grupo.Grupo
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "torneios")
data class Torneio(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var descricao: String,
    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    var grupo: Grupo
)
