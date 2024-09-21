package br.com.sporthub.reserva

import br.com.sporthub.horario.Horario
import br.com.sporthub.usuario.Usuario
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var dataReserva: LocalDateTime,
    var ativa: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    var horario: Horario,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario
)