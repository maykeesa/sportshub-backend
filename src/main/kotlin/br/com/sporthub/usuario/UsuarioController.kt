package br.com.sporthub.usuario

import br.com.sporthub.usuario.form.UsuarioForm
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuario")
class UsuarioController {

    @Autowired
    private lateinit var usuarioRep: UsuarioRepository
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @GetMapping()
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Usuario>>{
        var usuario: Page<Usuario> = this.usuarioRep.findAll(paginacao)

        return ResponseEntity.ok(usuario)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        var usuario: Optional<Usuario> = this.usuarioRep.findById(UUID.fromString(id))

        if(usuario.isPresent){
            return ResponseEntity.ok(usuario.get())
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping()
    fun save(@RequestBody @Valid usuarioForm: UsuarioForm): ResponseEntity<Usuario>{
        var usuario: Usuario = this.usuarioRep.save(ModelMapper().map(usuarioForm, Usuario::class.java))

        return ResponseEntity.ok(usuario)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid usuarioForm: UsuarioForm): ResponseEntity<Usuario>{
        val usuarioOpt: Optional<Usuario> = usuarioService.atualizarUsuario(id, usuarioForm)

        if(usuarioOpt.isPresent){
            var usuario = usuarioRep.save(usuarioOpt.get())
            return ResponseEntity.ok(usuario)
        }

        return  ResponseEntity.notFound().build()

    }

    @DeleteMapping
    fun delete(@PathVariable id: String): ResponseEntity<Any>{
        var usuarioOpt: Optional<Usuario> = this.usuarioRep.findById(UUID.fromString(id))

        if(usuarioOpt.isPresent){
            this.usuarioRep.delete(usuarioOpt.get())
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.noContent().build()
    }
}