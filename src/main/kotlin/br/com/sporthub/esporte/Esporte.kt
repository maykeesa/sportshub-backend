package br.com.sporthub.esporte

import br.com.sporthub.quadra.Quadra
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "esportes")
class Esporte(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var time: Boolean,
    var dupla: Boolean,
) {}