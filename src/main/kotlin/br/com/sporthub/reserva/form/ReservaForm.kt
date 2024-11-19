package br.com.sporthub.reserva.form

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Null

data class ReservaForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var dataReserva: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var ativa: Boolean,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horarioId: String,
    @Null
    var grupoId: String?
)