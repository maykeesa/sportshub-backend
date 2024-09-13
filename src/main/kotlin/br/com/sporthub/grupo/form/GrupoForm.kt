package br.com.sporthub.grupo.form

import jakarta.validation.constraints.NotBlank

data class GrupoForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var descricao: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var usuarios: ArrayList<String>
) {

}