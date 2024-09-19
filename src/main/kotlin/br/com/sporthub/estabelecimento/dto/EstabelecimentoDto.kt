package br.com.sporthub.estabelecimento.dto

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.quadra.Quadra
import br.com.sporthub.quadra.dto.QuadraDto
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class EstabelecimentoDto(
    var id: UUID,
    var nome: String,
    var email: String,
    var cnpj: String,
    var contato: String,
    var endereco: String,
    var cep: String,
    var descricao: String,
    var quadras: List<QuadraDto>,
    var dataCriacao: LocalDateTime,
    ) {

    constructor(estabelecimento: Estabelecimento): this(
        id = estabelecimento.id,
        nome = estabelecimento.nome,
        email = estabelecimento.email,
        cnpj = estabelecimento.cnpj,
        contato = estabelecimento.contato,
        endereco = estabelecimento.endereco,
        cep = estabelecimento.cep,
        descricao = estabelecimento.descricao,
        quadras = if(estabelecimento.quadras != null) estabelecimento.quadras.map { QuadraDto(it, false) } else ArrayList(),
        dataCriacao = estabelecimento.dataCriacao
    )
}