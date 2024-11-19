package br.com.sporthub.grupo

import br.com.sporthub.torneio.Torneio
import br.com.sporthub.usuario.Usuario
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "grupos")
data class Grupo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var descricao: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_criador_id", nullable = false)
    var usuarioCriador: Usuario,

    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_grupo",
        joinColumns = [JoinColumn(name = "grupo_id")],
        inverseJoinColumns = [JoinColumn(name = "usuario_id")]
    )
    @JsonManagedReference
    var usuarios: MutableList<Usuario> = mutableListOf(),

    @OneToMany(mappedBy = "grupo", cascade = [CascadeType.ALL], orphanRemoval = true)
    var torneios: List<Torneio>
){
    override fun toString(): String {
        return "Grupo(id=$id, " +
                "nome='$nome', " +
                "descricao='$descricao', " +
                "usuarioCriador='$usuarioCriador', " +
                "dataCriacao=$dataCriacao, " +
                "usuarios=${usuarios.map{ it.id }}, " +
                "torneios=$torneios)"
    }
}