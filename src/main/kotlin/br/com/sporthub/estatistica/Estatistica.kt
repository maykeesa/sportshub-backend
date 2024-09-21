package br.com.sporthub.estatistica

import br.com.sporthub.grupo.Grupo
import br.com.sporthub.usuario.Usuario
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "estatisticas")
data class Estatistica (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var data: LocalDate,

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    var nota : Double,
    var gols: Int,
    var vitorioas: Int,
    var partidas: Int,

    @JoinColumn(name = "grupo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var grupo: Grupo,

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var usuario: Usuario,
)