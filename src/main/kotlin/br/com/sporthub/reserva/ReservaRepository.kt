package br.com.sporthub.reserva

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ReservaRepository : JpaRepository<Reserva, UUID>