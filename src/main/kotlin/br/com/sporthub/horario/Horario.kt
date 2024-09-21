package br.com.sporthub.horario

import br.com.sporthub.quadra.Quadra
import jakarta.persistence.*
import java.time.LocalTime
import java.util.UUID

@Entity
data class Horario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id:UUID,
    var horarioInicio: LocalTime,
    var horarioFim: LocalTime,
    var duracao: LocalTime,
    var diaSemana: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quadra_id")
    var quadra: Quadra)