package br.com.sporthub.estatistica

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EstatisticaRepository: JpaRepository<Estatistica, UUID> {
}