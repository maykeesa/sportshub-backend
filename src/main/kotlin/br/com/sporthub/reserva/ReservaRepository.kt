package br.com.sporthub.reserva

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReservaRepository : JpaRepository<Reserva, UUID>{

    @Query("SELECT r FROM Reserva r WHERE r.horario.quadra.id = :quadraId AND r.dataReserva >= CURRENT_DATE")
    fun findReservasByQuadraId(@Param("quadraId") quadraId: UUID): List<Reserva>

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId ORDER BY r.dataReserva")
    fun findReservasByUsuarioId(@Param("usuarioId") usuarioId: UUID): List<Reserva>

    fun findAllByAtivaOrderByDataReservaAsc(ativa: Boolean): List<Reserva>
}