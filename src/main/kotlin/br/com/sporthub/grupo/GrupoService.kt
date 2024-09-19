package br.com.sporthub.grupo

import br.com.sporthub.service.GenericService
import br.com.sporthub.usuario.Usuario
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GrupoService : GenericService<Grupo>(Grupo::class.java){

    @Transactional
    fun addUsuario(grupo: Grupo, usuario: Usuario) {
        if (grupo.usuarios != null && usuario != null) {
            grupo.usuarios.add(usuario)
            this.entityManager.merge(grupo)
        } else {
            throw IllegalArgumentException("Grupo ou Usuário não podem ser nulos")
        }
    }
}