package br.com.sporthub.quadra.dto

import br.com.sporthub.quadra.Quadra
import java.util.*

data class QuadraDto(
    var id: UUID,
    var capacidade: Int,
    var estabelecimento: Any?,
    var horarios: List<Any>
    ) {

    constructor(quadra: Quadra, estabelecimentoCampo: Boolean = true): this(
        id = quadra.id,
        capacidade = quadra.capacidade,
        estabelecimento = if(estabelecimentoCampo) quadra.estabelecimento else quadra.estabelecimento.id,
        horarios = if(quadra.horarios != null) quadra.horarios.map { it.id } else ArrayList()
    )
}