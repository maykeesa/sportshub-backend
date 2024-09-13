package br.com.sporthub.usuario

import br.com.sporthub.grupo.Grupo
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var cpf: String,
    var nome: String,
    var email: String,
    var senha: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToMany(mappedBy = "usuarios")
    @JsonBackReference
    var grupos: List<Grupo> = ArrayList()
){
    override fun toString(): String {
        return "Usuario(id=$id, " +
                "cpf='$cpf', " +
                "nome='$nome', " +
                "email='$email', " +
                "senha='$senha', " +
                "dataNascimento=$dataNascimento, " +
                "genero='$genero', " +
                "telefone='$telefone', " +
                "dataCriacao=$dataCriacao, " +
                "grupos=${grupos.map { it.id }})"
    }
}