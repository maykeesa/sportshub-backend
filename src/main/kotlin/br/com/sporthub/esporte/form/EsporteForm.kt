package br.com.sporthub.esporte.form

import jakarta.validation.constraints.NotBlank

data class EsporteForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var time: Boolean,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var dupla: Boolean
)