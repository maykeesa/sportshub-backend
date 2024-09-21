package br.com.sporthub.quadra.dto

import br.com.sporthub.esporte.dto.EsporteDto
import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.quadra.Quadra
import java.util.*
import kotlin.collections.ArrayList

data class QuadraDto(
    var id: UUID?,
    var capacidade: Int,
    var descricao: String?,
    var estabelecimento: Any,
    var nota: Double?,
    var valorHora: Double?,
    var esportes: List<EsporteDto>,
    var horarios: List<HorarioDto>
    ) {

    constructor(quadra: Quadra, estabelecimentoCampo: Boolean = true): this(
        id = quadra.id,
        capacidade = quadra.capacidade,
        estabelecimento = if(estabelecimentoCampo) EstabelecimentoDto(quadra.estabelecimento) else quadra.estabelecimento.id,
        horarios = if(quadra.horarios.isNotEmpty()) quadra.horarios.map { HorarioDto(it) } else ArrayList(),
        nota = quadra.nota,
        descricao = quadra.descricao,
        valorHora = quadra.valorHora,
        esportes = quadra.esportes.map { EsporteDto(it) }.toMutableList()
    )
}