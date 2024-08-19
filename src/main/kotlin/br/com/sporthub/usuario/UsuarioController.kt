package br.com.sporthub.usuario

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(name = "/usuario")
class UsuarioController {

    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    @GetMapping()
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Usuario>>{

        var usuario: Page<Usuario> = this.usuarioRep.findAll(paginacao)

        if(!usuario.isEmpty){
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID): ResponseEntity<Usuario>{
        var usuario: Optional<Usuario> = this.usuarioRep.findById(id)

        if(usuario.isPresent){
            return ResponseEntity.ok(usuario.get())
        }

        return ResponseEntity.notFound().build()
    }
}