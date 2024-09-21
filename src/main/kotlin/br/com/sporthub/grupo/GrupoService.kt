package br.com.sporthub.grupo

import br.com.sporthub.service.GenericService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService : GenericService<Grupo>(Grupo::class.java){

    @Autowired
    private lateinit var grupoRep: GrupoRespository

    fun addUsuario(grupo: Grupo, usuario: Usuario) {
        grupo.usuarios.add(usuario)
        this.grupoRep.save(grupo)
    }
}
