package br.com.sporthub.usuario.form

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class UsuarioForm (
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var cpf: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var nome: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    @Email(message = "O Email não é válido.")
    var email: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var senha: String,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var dataNascimento: LocalDate,
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var genero: String,
    @Min(value = 10)
    @Max(value = 11)
    @NotBlank(message = "O campo não pode ser vazio ou nulo.")
    var telefone: String){
}