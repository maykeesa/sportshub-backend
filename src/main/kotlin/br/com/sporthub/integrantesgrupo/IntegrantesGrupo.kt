package br.com.sporthub.integrantesgrupo


import jakarta.persistence.*
import java.util.*
import br.com.sporthub.grupo.Grupo
import br.com.sporthub.usuario.Usuario

@Entity
@Table(name = "integrantes_grupo")
class IntegrantesGrupo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    var grupo: Grupo,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario,
){
}