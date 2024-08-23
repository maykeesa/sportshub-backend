package br.com.sporthub.estatistica

import br.com.sporthub.grupo.Grupo
import br.com.sporthub.usuario.Usuario
import jakarta.persistence.*
import java.time.LocalTime
import java.util.UUID
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin

@Entity
@Table(name = "estatisticas")
data class Estatistica (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var data: LocalTime,

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
){
}