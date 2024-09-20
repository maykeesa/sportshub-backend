package br.com.sporthub.auth

import com.example.auth.domain.user.UserRole
import java.time.LocalDate

class RegisterDto(
    var nome: String,
    var cpf: String,
    var email: String,
    var senha: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
) {
}