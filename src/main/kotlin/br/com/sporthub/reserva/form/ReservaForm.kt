package br.com.sporthub.reserva.form

import br.com.sporthub.horario.Horario
import br.com.sporthub.usuario.Usuario
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class ReservaForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var dataReserva: LocalDate,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var ativa: Boolean,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horario: Horario,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var usuario: Usuario
    )