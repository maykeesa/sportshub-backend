package br.com.sporthub.estabelecimento

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID


interface EstabelecimentoRepository : JpaRepository<Estabelecimento, UUID> {


}