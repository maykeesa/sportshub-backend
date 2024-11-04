package br.com.sporthub.jogo

import br.com.sporthub.jogo.enums.StatusEnum
import br.com.sporthub.torneio.Torneio
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "jogos")
data class Jogo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var timeA: String,
    var timeB: String,
    var resultadoTimeA: Int = 0,
    var resultadoTimeB: Int = 0,
    var timeVencedor: String,
    var status: StatusEnum,

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false)
    var torneio: Torneio,

    @CreationTimestamp
    var dataCriacao: LocalDateTime

)