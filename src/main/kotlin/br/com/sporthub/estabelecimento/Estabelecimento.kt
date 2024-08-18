package br.com.sporthub.estabelecimento

import br.com.sporthub.quadra.Quadra
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "estabelecimentos")
data class Estabelecimento(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var email: String,
    var cnpj: String,
    var contato: String,
    var endereco: String,
    var cep: String,
    var descricao: String,
    @OneToMany(mappedBy = "estabelecimento", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val quadras: List<Quadra>
) {
}