package br.com.sporthub.grupo

import br.com.sporthub.quadra.Quadra
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "grupos")
class Grupo (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var descricao: String,
    var dataCriacao: Date,
){
}