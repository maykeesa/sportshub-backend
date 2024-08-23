package br.com.sporthub.esporte

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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/esporte")
class EsporteController {

    @Autowired
    private lateinit var esporteRep: EsporteRepository

    @GetMapping
    @Operation(summary = "Listar todos os esportes")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de esportes"),
        ApiResponse(responseCode = "204", description = "Não há esportes cadastrados")
    ])
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Esporte>> {
        var esporte: Page<Esporte> = this.esporteRep.findAll(paginacao)

        if (!esporte.isEmpty) {
            return ResponseEntity.ok(esporte)
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um esporte pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um esporte"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun getOne(id: UUID): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (esporte.isPresent) {
            return ResponseEntity.ok(esporte.get())
        }

        return ResponseEntity.notFound().build()
    }

    // Corrigir, utilizar o Form
    /*
    @PostMapping()
    @Operation(summary = "Cadastrar um novo esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Esporte cadastrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao cadastrar esporte")
    ])
    fun save(esporte: EsporteForm): ResponseEntity<Esporte> {
        var esporte: Esporte = this.esporteRep.save(esporte)

        return ResponseEntity.status(201).body(esporte)
    }
    */

    // Não ta fazendo nd
    /*
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Esporte atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun update(id: UUID, esporte: Esporte): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (esporte.isPresent) {
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }
     */

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Esporte deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun delete(id: String): ResponseEntity<Esporte> {
        var esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if (esporteOpt.isPresent) {
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }
}