package br.com.sporthub.reserva

import br.com.sporthub.grupo.Grupo
import br.com.sporthub.horario.Horario
import br.com.sporthub.usuario.Usuario
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var dataReserva: LocalDate,
    var ativa: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    var horario: Horario,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = true)
    var grupo: Grupo?

)