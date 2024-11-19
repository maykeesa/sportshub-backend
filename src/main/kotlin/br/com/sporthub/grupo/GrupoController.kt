package br.com.sporthub.grupo

import br.com.sporthub.config.security.AuthorizationService
import br.com.sporthub.grupo.dto.GrupoDto
import br.com.sporthub.grupo.form.GrupoForm
import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import br.com.sporthub.usuario.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/grupo")
class GrupoController {

    @Autowired
    private lateinit var authService: AuthorizationService
    @Autowired
    private lateinit var grupoService: GrupoService
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var grupoRep: GrupoRespository
    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    @GetMapping
    @Operation(summary = "Listar todos os grupos")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de grupos"),
        ApiResponse(responseCode = "204", description = "Não há grupos cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<GrupoDto>> {
        val gruposPage: Page<Grupo> = this.grupoRep.findAll(paginacao)
        val gruposDtoPage: Page<GrupoDto> = gruposPage.map { grupo -> GrupoDto(grupo) }

        return ResponseEntity.ok(gruposDtoPage)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um grupo pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um grupo"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val grupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))

        if(grupoOpt.isPresent){
            return ResponseEntity.ok(GrupoDto(grupoOpt.get()))
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Salvar um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o grupo salvo"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun save(@RequestBody @Valid grupoForm: GrupoForm): ResponseEntity<Any>{
        val mapper = UtilsService.getGenericModelMapper()
        val grupo: Grupo = mapper.map(grupoForm, Grupo::class.java)

        val usuarioLogado: Usuario = this.authService.getUsuarioLogado() as Usuario
        grupoForm.usuarios.add(usuarioLogado.id.toString())

        val usuarios: ArrayList<Usuario> = this.usuarioService.getListUsuarios(grupoForm)
        grupo.usuarios = usuarios
        grupo.usuarioCriador = usuarioLogado

        val grupoPersistido: Grupo = this.grupoRep.save(grupo)
        return ResponseEntity.status(201).body(GrupoDto(grupoPersistido))
    }

    @PostMapping("/{id}/usuario/{emailUsuario}")
    @Operation(summary = "Adicionar um usuário a um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o grupo com o usuário adicionado"),
        ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado")
    ])
    fun addUsuario(@PathVariable id: String, @PathVariable emailUsuario: String): ResponseEntity<Any>{
        val grupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))
        val usuarioOpt: Optional<Usuario> = this.usuarioRep.findByEmail(emailUsuario)

        if (grupoOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Grupo não encontrado/existe."))
        }

        if (usuarioOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Usuário não encontrado/existe."))
        }

        val grupo: Grupo = grupoOpt.get()
        this.grupoService.addUsuario(grupo, usuarioOpt.get())

        return ResponseEntity.ok(GrupoDto(grupo))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o grupo atualizado"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody grupoForm: Map<String,Any>): ResponseEntity<Any>{
        val grupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))

        if (grupoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val grupoAtualizado = this.grupoService.atualizarEntidade(grupoOpt.get(), grupoForm)
        return ResponseEntity.status(202).body(GrupoDto(grupoAtualizado as Grupo))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Grupo deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void>{
        val grupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))

        if(grupoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.grupoRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
}