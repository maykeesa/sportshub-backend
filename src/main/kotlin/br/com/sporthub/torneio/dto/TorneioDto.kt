package br.com.sporthub.torneio.dto

import br.com.sporthub.grupo.dto.GrupoDto
import br.com.sporthub.jogo.dto.JogoDto
import br.com.sporthub.torneio.Torneio
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class TorneioDto(
    var id: UUID,
    var nome: String,
    var descricao: String,
    var grupo: GrupoDto,
    var jogos: List<JogoDto>,
    var dataCriacao: LocalDateTime
) {
    constructor(torneio: Torneio): this(
        id = torneio.id,
        nome = torneio.nome,
        descricao = torneio.descricao,
        grupo = GrupoDto(torneio.grupo),
        jogos = if (torneio.jogos != null) torneio.jogos.map { JogoDto(it) } else ArrayList(),
        dataCriacao = torneio.dataCriacao
    )
}