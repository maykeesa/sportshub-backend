package br.com.sporthub.estabelecimento

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EstabelecimentoRepository : JpaRepository<Estabelecimento, UUID> {

    fun findByEmail(email: String): Optional<Estabelecimento>
}