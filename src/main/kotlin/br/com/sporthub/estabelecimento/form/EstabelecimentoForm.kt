package br.com.sporthub.estabelecimento.form

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class EstabelecimentoForm(
    @NotEmpty(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    @Email(message = "O Email não é válido.")
    var email: String,
    @NotBlank(message = "O campo senha não pode ser vazio ou nulo.")
    var senha: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var cnpj: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var contato: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var endereco: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var cep: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var descricao: String
)
