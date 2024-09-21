package br.com.sporthub.estabelecimento

import br.com.sporthub.quadra.Quadra
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
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
    @JsonBackReference
    val quadras: List<Quadra>,
    @CreationTimestamp
    var dataCriacao: LocalDateTime,
)