package br.com.sporthub.quadra

import br.com.sporthub.esporte.Esporte
import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.horario.Horario
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "quadras")
data class Quadra(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,
    var capacidade: Int,
    var nota : Double,
    var descricao : String,
    var valorHora : Double,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "quadra_esporte",
        joinColumns = [JoinColumn(name = "quadra_id")],
        inverseJoinColumns = [JoinColumn(name = "esporte_id")]
    )
    @JsonManagedReference
    var esportes: List<Esporte> = ArrayList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    @JsonManagedReference
    var estabelecimento: Estabelecimento,

    @OneToMany(mappedBy = "quadra", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var horarios: List<Horario> = ArrayList()
) {
    override fun toString(): String {
        return "Quadra(" +
                "id=$id, " +
                "capacidade=$capacidade, " +
                "nota=$nota, descricao='$descricao', " +
                "valorHora=$valorHora, esportes=$esportes, " +
                "estabelecimento=${estabelecimento.id}, " +
                "horarios=$horarios)"
    }
}
