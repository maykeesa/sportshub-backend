package br.com.sporthub.torneio

import br.com.sporthub.grupo.Grupo
import br.com.sporthub.jogo.Jogo
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "torneios")
data class Torneio(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var descricao: String,

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    var grupo: Grupo,

    @OneToMany(mappedBy = "torneio", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jogos: List<Jogo> = ArrayList(),

    @CreationTimestamp
    var dataCriacao: LocalDateTime

)
