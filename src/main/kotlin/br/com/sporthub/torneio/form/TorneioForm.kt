package br.com.sporthub.torneio.form

import br.com.sporthub.grupo.Grupo
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*

class TorneioForm(

    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var dataCriacao: LocalDateTime,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var descricao: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var grupo: Grupo
) {
}