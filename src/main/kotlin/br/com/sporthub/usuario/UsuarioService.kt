package br.com.sporthub.usuario

import br.com.sporthub.usuario.form.UsuarioForm
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService {

    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    fun atualizarUsuario(id: UUID, usuarioForm: UsuarioForm): Optional<Usuario> {
        val usuarioOpt: Optional<Usuario> = this.usuarioRep.findById(id)

        if(usuarioOpt.isPresent){
            val usuario = usuarioOpt.get()
            BeanUtils.copyProperties(usuarioForm, usuario);
            usuario.id = id

            return Optional.of(usuario)
        }

        return Optional.empty()
    }
}