package br.com.sporthub.grupo.dto

import br.com.sporthub.grupo.Grupo
import java.time.LocalDateTime
import java.util.*

data class GrupoDto(
    var id: UUID?,
    var nome: String?,
    var descricao: String?,
    var usuarioCriador: UUID?,
    var usuarios: List<UUID>?,
    var dataCriacao: LocalDateTime?
) {
    constructor(grupo: Grupo?): this(
        id = grupo?.id,
        nome = grupo?.nome,
        descricao = grupo?.descricao,
        usuarioCriador = grupo?.usuarioCriador?.id,
        usuarios = grupo?.usuarios?.map { it.id },
        dataCriacao = grupo?.dataCriacao
    )
}