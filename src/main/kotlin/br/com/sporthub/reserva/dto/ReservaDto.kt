package br.com.sporthub.reserva.dto

import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.reserva.Reserva
import br.com.sporthub.usuario.dto.UsuarioDto
import java.time.LocalDate
import java.util.*

class ReservaDto(
    var id: UUID?,
    var dataReserva: LocalDate,
    var ativa: Boolean,
    var horario: HorarioDto,
    var usuario: UsuarioDto
) {
    constructor(reserva: Reserva) : this(
        id = reserva.id,
        dataReserva = reserva.dataReserva,
        ativa = reserva.ativa,
        horario = HorarioDto(reserva.horario),
        usuario = UsuarioDto(reserva.usuario)
    )
}