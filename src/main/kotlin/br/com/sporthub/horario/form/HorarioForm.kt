package br.com.sporthub.horario.form

import br.com.sporthub.quadra.Quadra
import jakarta.validation.constraints.NotBlank
import java.time.LocalTime

data class HorarioForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horarioInicio: LocalTime,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var horarioFim: LocalTime,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var duracao: LocalTime,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var diaSemana: Int,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var quadra: Quadra
) {
}