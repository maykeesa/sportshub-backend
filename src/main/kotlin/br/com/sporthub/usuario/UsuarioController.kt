package br.com.sporthub.usuario

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/usuario")
class UsuarioController {

    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    @GetMapping()
    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários"),
        ApiResponse(responseCode = "204", description = "Não há usuários cadastrados")
    ])
    fun getAllUsuarios(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Usuario>>{

        var usuario: Page<Usuario> = this.usuarioRep.findAll(paginacao)

        if(!usuario.isEmpty){
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um usuário"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun getOneUsuario(@PathVariable id: UUID): ResponseEntity<Usuario>{
        var usuario: Optional<Usuario> = this.usuarioRep.findById(id)

        if(usuario.isPresent){
            return ResponseEntity.ok(usuario.get())
        }

        return ResponseEntity.notFound().build()
    }


    @PostMapping()
    @Operation(summary = "Salvar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o usuário salvo"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun saveUsuario(usuario: Usuario): ResponseEntity<Usuario>{
        var usuario: Usuario = this.usuarioRep.save(usuario)

        if (usuario == null){
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(usuario)
    }

    @DeleteMapping
    @Operation(summary = "Deletar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun deleteUsuario(@PathVariable id: UUID): ResponseEntity<Void>{
        this.usuarioRep.deleteById(id)

        if(this.usuarioRep.findById(id).isPresent){
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.noContent().build()
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun updateUsuario(@PathVariable id: UUID, usuario: Usuario): ResponseEntity<Usuario>{
        var usuario: Usuario = this.usuarioRep.save(usuario)

        if (usuario == null){
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(usuario)
    }
}