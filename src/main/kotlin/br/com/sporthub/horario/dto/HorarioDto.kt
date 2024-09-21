package br.com.sporthub.horario.dto

import br.com.sporthub.horario.Horario
import java.time.LocalTime
import java.util.*

data class HorarioDto(
    var id: UUID,
    var horarioInicio: LocalTime,
    var horarioFim: LocalTime,
    var duracao: LocalTime,
    var diaSemana: Int,
    var quadra: UUID
) {
    constructor(horario: Horario): this(
        id = horario.id,
        horarioInicio = horario.horarioInicio,
        horarioFim = horario.horarioFim,
        duracao = horario.duracao,
        diaSemana = horario.diaSemana,
        quadra = horario.quadra.id
    )
}