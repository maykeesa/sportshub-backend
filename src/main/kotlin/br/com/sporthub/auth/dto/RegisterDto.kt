package br.com.sporthub.auth.dto

import java.time.LocalDate

data class RegisterDto(
    var nome: String,
    var cpf: String,
    var email: String,
    var senha: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
)