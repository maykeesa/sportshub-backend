package br.com.sporthub.quadra

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface QuadraRepository : JpaRepository<Quadra, UUID> {

    fun findByEstabelecimento_Id(estabelecimentoId: UUID): List<Quadra>
}
