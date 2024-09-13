package br.com.sporthub.usuario

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class UsuarioService : GenericService<Usuario>(Usuario::class.java){
}