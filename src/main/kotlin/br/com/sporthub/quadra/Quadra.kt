package br.com.sporthub.quadra

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.horario.Horario
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "quadras")
data class Quadra(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var capacidade: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    @JsonManagedReference
    var estabelecimento: Estabelecimento,

    @OneToMany(mappedBy = "quadra", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var horarios: List<Horario>
) {
    override fun toString(): String {
        return "Quadra(" +
                "id=$id, " +
                "capacidade=$capacidade, " +
                "estabelecimento=$estabelecimento, " +
                "horarios=$horarios)"
    }
}