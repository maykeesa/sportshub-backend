package br.com.sporthub.jogo.form

import br.com.sporthub.torneio.Torneio
import jakarta.validation.constraints.NotBlank

class JogoForm(
    @NotBlank
    var torneio: Torneio,
) {
}