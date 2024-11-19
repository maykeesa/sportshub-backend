package br.com.sporthub.jogo.form

import jakarta.validation.constraints.NotBlank

data class JogoForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var timeA: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var timeB: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var torneioId: String
)