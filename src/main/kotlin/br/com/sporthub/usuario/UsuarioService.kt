package br.com.sporthub.usuario

import br.com.sporthub.quadra.Quadra
import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class UsuarioService : GenericService<Quadra>(Quadra::class.java){
}