package br.com.sporthub.estatistica.form

import jakarta.validation.constraints.NotBlank

data class EstatisticaForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var data: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nota: Double,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var gols: Int,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var vitorias: Int,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var partidas: Int,
    var grupoId: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var usuarioId: String
)