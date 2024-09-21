package br.com.sporthub.quadra.dto

import br.com.sporthub.esporte.Esporte
import br.com.sporthub.esporte.dto.EsporteDto
import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.quadra.Quadra
import java.util.*

data class QuadraDto(
    var id: UUID,
    var capacidade: Int,
    var estabelecimento: Any,
    var horarios: List<HorarioDto>,
    var nota: Double? = null,
    var descricao: String? = null,
    var valorHora: Double? = null,
    var esportes: MutableList<EsporteDto> = ArrayList()
    ) {

    constructor(quadra: Quadra, estabelecimentoCampo: Boolean = true): this(
        id = quadra.id,
        capacidade = quadra.capacidade,
        estabelecimento = if(estabelecimentoCampo) EstabelecimentoDto(quadra.estabelecimento) else quadra.estabelecimento.id,
        horarios = quadra.horarios.map { HorarioDto(it) },
        nota = quadra.nota,
        descricao = quadra.descricao,
        valorHora = quadra.valorHora,
        esportes = quadra.esportes.map { EsporteDto(it) }.toMutableList()
    )
}