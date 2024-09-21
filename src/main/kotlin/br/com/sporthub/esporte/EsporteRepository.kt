package br.com.sporthub.esporte

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EsporteRepository : JpaRepository<Esporte,UUID>