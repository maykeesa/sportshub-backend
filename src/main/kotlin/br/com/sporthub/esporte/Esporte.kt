package br.com.sporthub.esporte

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "esportes")
data class Esporte(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var time: Boolean,
    var dupla: Boolean,
) {

}