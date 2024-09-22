package br.com.sporthub.quadra.dto

import br.com.sporthub.esporte.dto.EsporteDto
import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.quadra.Quadra
import br.com.sporthub.reserva.Reserva
import java.util.*
import kotlin.collections.ArrayList

data class QuadraDto(
    var id: UUID?,
    var nome: String,
    var nota: Double?,
    var valorHora: Double?,
    var capacidade: Int,
    var descricao: String?,
    var imageUrl : String,
    var estabelecimento: Any,
    var esportes: List<EsporteDto>,
    var horarios: List<HorarioDto>,
    var reservas: List<Reserva> = ArrayList()
    ) {

    constructor(quadra: Quadra, estabelecimentoCampo: Boolean = true): this(
        id = quadra.id,
        nome = quadra.nome,
        capacidade = quadra.capacidade,
        estabelecimento = if(estabelecimentoCampo) EstabelecimentoDto(quadra.estabelecimento) else quadra.estabelecimento.id,
        horarios = if(quadra.horarios.isNotEmpty()) quadra.horarios.map { HorarioDto(it) } else ArrayList(),
        nota = quadra.nota,
        descricao = quadra.descricao,
        valorHora = quadra.valorHora,
        esportes = quadra.esportes.map { EsporteDto(it) }.toMutableList(),
        imageUrl = quadra.imageUrl
    )

    constructor(quadra: Quadra, reservas: List<Reserva>, estabelecimentoCampo: Boolean = true) : this(
        id = quadra.id,
        nome = quadra.nome,
        imageUrl = quadra.imageUrl,
        capacidade = quadra.capacidade,
        estabelecimento = if(estabelecimentoCampo) EstabelecimentoDto(quadra.estabelecimento) else quadra.estabelecimento.id,
        horarios = if(quadra.horarios.isNotEmpty()) quadra.horarios.map { HorarioDto(it) } else ArrayList(),
        nota = quadra.nota,
        descricao = quadra.descricao,
        valorHora = quadra.valorHora,
        esportes = quadra.esportes.map { EsporteDto(it) }.toMutableList(),
        reservas = reservas
    )
}