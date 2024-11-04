package br.com.sporthub.jogo.dto

import br.com.sporthub.jogo.Jogo
import br.com.sporthub.jogo.enums.StatusEnum
import br.com.sporthub.torneio.Torneio
import br.com.sporthub.torneio.dto.TorneioDto
import java.time.LocalDateTime
import java.util.*

data class JogoDto(
    var id: UUID,
    var timeA: String,
    var timeB: String,
    var resultadoTimeA: String,
    var resultadoTimeB: String,
    var timeVencedor: String,
    var status: StatusEnum,
    var torneio: UUID,
    var dataCriacao: LocalDateTime
) {
    constructor(jogo: Jogo): this(
        id = jogo.id,
        timeA = jogo.timeA,
        timeB = jogo.timeB,
        resultadoTimeA = jogo.resultadoTimeA,
        resultadoTimeB = jogo.resultadoTimeB,
        timeVencedor = jogo.timeVencedor,
        status = jogo.status,
        torneio = jogo.torneio.id,
        dataCriacao = jogo.dataCriacao
    )
}