package br.com.sporthub.esporte

import br.com.sporthub.esporte.dto.EsporteDto
import br.com.sporthub.esporte.form.EsporteForm
import br.com.sporthub.service.UtilsService
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
@RequestMapping("/esporte")
class EsporteController {

    @Autowired
    private lateinit var esporteRep: EsporteRepository

    @Autowired
    private lateinit var esporteService: EsporteService

    @GetMapping
    @Operation(summary = "Listar todos os esportes")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de esportes"),
        ApiResponse(responseCode = "204", description = "Não há esportes cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<EsporteDto>> {
        val esporte: Page<Esporte> = this.esporteRep.findAll(paginacao)

        val esporteDto: Page<EsporteDto> = esporte.map { esporte -> EsporteDto(esporte) }

        return ResponseEntity.ok(esporteDto)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um esporte pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um esporte"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Esporte> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if (esporteOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(esporteOpt.get())
    }

    @PostMapping
    @Operation(summary = "Salvar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Esporte salvo com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao salvar o esporte")
    ])
    fun save(@RequestBody @Valid esporteForm: EsporteForm): ResponseEntity<Esporte> {
        val mapper = UtilsService.getGenericModelMapper()
        val esporte: Esporte = this.esporteRep.save(mapper.map(esporteForm, Esporte::class.java))

        return ResponseEntity.status(201).body(esporte)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o esporte atualizado"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody esporteForm: Map<String, Any>): ResponseEntity<Any> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if (esporteOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val esporteAtualizado = this.esporteService.atualizarEntidade(esporteOpt.get(), esporteForm)
        return ResponseEntity.status(202).body(esporteAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Esporte deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Esporte> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if(esporteOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.esporteRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
}