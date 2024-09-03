package br.com.sporthub.jogo.form

import br.com.sporthub.torneio.Torneio
import jakarta.validation.constraints.NotBlank

data class JogoForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var torneio: Torneio,
) {
}