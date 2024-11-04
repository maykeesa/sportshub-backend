package br.com.sporthub.torneio.form

import jakarta.validation.constraints.NotBlank

data class TorneioForm(

    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var descricao: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var grupoId: String
)