package br.com.sporthub.usuario

import br.com.sporthub.grupo.form.GrupoForm
import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class UsuarioService : GenericService<Usuario>(Usuario::class.java){

    fun getListUsuarios(grupoForm: GrupoForm): ArrayList<Usuario>{
        val usuariosAny: ArrayList<Any> = transformarListIdToEntity(grupoForm.usuarios)
        val usuarios: ArrayList<Usuario> = ArrayList(usuariosAny.map { it as Usuario })
        grupoForm.usuarios = ArrayList()

        return usuarios
    }

}