package br.com.sporthub.esporte.dto


import br.com.sporthub.esporte.Esporte
import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.quadra.Quadra
import br.com.sporthub.quadra.dto.QuadraDto
import java.util.*

class EsporteDto(
    var id: UUID,
    var nome: String,
) {
    constructor(esporte: Esporte): this(
        id = esporte.id,
        nome = esporte.nome
    )
}