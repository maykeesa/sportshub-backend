package br.com.sporthub.quadra

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.horario.Horario
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
    val estabelecimento: Estabelecimento,

    @OneToMany(mappedBy = "quadra", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var horarios: List<Horario>
) {

}