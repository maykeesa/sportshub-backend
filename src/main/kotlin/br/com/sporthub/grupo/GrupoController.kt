package br.com.sporthub.grupo

import br.com.sporthub.grupo.form.GrupoForm
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
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/grupo")
class GrupoController {

    @Autowired
    private lateinit var grupoService: GrupoService
    @Autowired
    private lateinit var grupoRep: GrupoRespository

    @GetMapping
    @Operation(summary = "Listar todos os grupos")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de grupos"),
        ApiResponse(responseCode = "204", description = "Não há grupos cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Grupo>> {
        val grupos: Page<Grupo> = this.grupoRep.findAll(paginacao)

        return ResponseEntity.ok(grupos)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um grupo pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um grupo"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val grupo: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))

        if(grupo.isPresent){
            return ResponseEntity.ok(grupo.get())
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Salvar um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o grupo salvo"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun save(@RequestBody @Valid grupoForm: GrupoForm): ResponseEntity<Grupo>{
        val grupo: Grupo = this.grupoRep.save(ModelMapper().map(grupoForm, Grupo::class.java))

        return ResponseEntity.status(201).body(grupo)
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
        return ResponseEntity.status(202).body(grupoAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Grupo deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void>{
        val GrupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(id))

        if(GrupoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.grupoRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
}