package br.com.sporthub.quadra.form

import jakarta.validation.constraints.NotBlank

data class QuadraForm(
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var capacidade: Int,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var estabelecimentoId: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var esportes: List<String>,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nota: Double,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var descricao: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var valorHora: Double
) {

}