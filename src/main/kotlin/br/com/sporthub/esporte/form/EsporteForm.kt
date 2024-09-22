package br.com.sporthub.esporte.form

import jakarta.validation.constraints.NotBlank

data class      EsporteForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
)