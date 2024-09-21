package br.com.sporthub.horario

import br.com.sporthub.quadra.Quadra
import com.fasterxml.jackson.annotation.JsonBackReference
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
    @JsonBackReference
    var quadra: Quadra) {

    override fun toString(): String {
        return "Horario(" +
                "id=$id, " +
                "horarioInicio=$horarioInicio, " +
                "horarioFim=$horarioFim, " +
                "duracao=$duracao, " +
                "diaSemana=$diaSemana, " +
                "quadra=${quadra.id})"
    }
}
