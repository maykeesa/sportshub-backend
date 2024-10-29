package br.com.sporthub.usuario.dto

import br.com.sporthub.usuario.UserRole
import br.com.sporthub.usuario.Usuario
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class UsuarioDto(
    var id: UUID,
    var cpf: String,
    var nome: String,
    var email: String,
    var roles: UserRole,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
    var grupos: List<UUID>,
    var dataCriacao: LocalDateTime
){
    constructor(usuario: Usuario): this(
        id = usuario.id,
        cpf = usuario.cpf,
        nome = usuario.nome,
        email = usuario.email,
        roles = usuario.role,
        dataNascimento = usuario.dataNascimento,
        genero = usuario.genero,
        telefone = usuario.telefone,
        grupos = if (usuario.grupos != null) usuario.grupos.map { it.id } else ArrayList(),
        dataCriacao = usuario.dataCriacao
    )
}