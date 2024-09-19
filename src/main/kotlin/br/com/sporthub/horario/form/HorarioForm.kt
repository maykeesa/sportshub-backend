package br.com.sporthub.horario.form

import jakarta.validation.constraints.NotBlank

data class HorarioForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horarioInicio: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horarioFim: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var diaSemana: Int,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var quadraId: String
) {
}