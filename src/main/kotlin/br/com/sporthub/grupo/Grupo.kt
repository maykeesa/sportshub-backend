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
    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = [JoinColumn(name = "grupo_id")],
        inverseJoinColumns = [JoinColumn(name = "usuario_id")]
    )
    @JsonManagedReference
    var usuarios: List<Usuario>,

    @OneToMany(mappedBy = "grupo", cascade = [CascadeType.ALL], orphanRemoval = true)
    var torneios: List<Torneio>
){
    override fun toString(): String {
        return "Grupo(id=$id, " +
                "nome='$nome', " +
                "descricao='$descricao', " +
                "dataCriacao=$dataCriacao, " +
                "usuarios=${usuarios.map{ it.id }}, " +
                "torneios=$torneios)"
    }
}