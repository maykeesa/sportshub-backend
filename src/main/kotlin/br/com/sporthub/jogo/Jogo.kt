package br.com.sporthub.jogo

import br.com.sporthub.torneio.Torneio
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "jogos")
class Jogo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false)
    var torneio: Torneio

) {
}