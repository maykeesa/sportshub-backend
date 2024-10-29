package br.com.sporthub.estabelecimento.dto

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.quadra.dto.QuadraDto
import br.com.sporthub.usuario.UserRole
import java.time.LocalDateTime
import java.util.*

data class EstabelecimentoDto(
    var id: UUID,
    var nome: String,
    var email: String,
    var role: UserRole,
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
        role = estabelecimento.role,
        cnpj = estabelecimento.cnpj,
        contato = estabelecimento.contato,
        endereco = estabelecimento.endereco,
        cep = estabelecimento.cep,
        descricao = estabelecimento.descricao,
        quadras = if(estabelecimento.quadras != null) estabelecimento.quadras.map { QuadraDto(it, false) } else ArrayList(),
        dataCriacao = estabelecimento.dataCriacao
    )
}