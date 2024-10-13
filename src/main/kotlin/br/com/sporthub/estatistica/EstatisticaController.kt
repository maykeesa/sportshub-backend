package br.com.sporthub.estatistica

import br.com.sporthub.estatistica.form.EstatisticaForm
import br.com.sporthub.grupo.GrupoRespository
import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/estatistica")
class EstatisticaController {

    @Autowired
    private lateinit var estatisticaService: EstatisticaService

    @Autowired
    private lateinit var estatisticaRep: EstatisticaRepository
    @Autowired
    private lateinit var grupoRep: GrupoRespository
    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    @GetMapping
    @Operation(summary = "Listar todas as estatísticas")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de estatísticas"),
        ApiResponse(responseCode = "204", description = "Não há estatísticas cadastradas")
    ])
    fun getAll(@PageableDefault(sort = ["id"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Estatistica>> {
        val estatisticas: Page<Estatistica> = this.estatisticaRep.findAll(paginacao)

        return ResponseEntity.ok(estatisticas)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma estatística pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma estatística"),
        ApiResponse(responseCode = "404", description = "Estatística não encontrada")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val estatistica: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (estatistica.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(estatistica.get())
    }

    @PostMapping
    @Operation(summary = "Salvar uma estatística")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Estatística salva com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário ou Grupo não encontrado/existe")
    ])
    fun save(@RequestBody @Valid estatisticaForm: EstatisticaForm): ResponseEntity<Any> {
        val mapper = UtilsService.getGenericModelMapper()

        val estatistica = mapper.map(estatisticaForm, Estatistica::class.java)
        estatistica.data = UtilsService.dataStringToLocalDate(estatisticaForm.data)

        val usuarioOpt = this.usuarioRep.findById(UUID.fromString(estatisticaForm.usuarioId))
        val grupoOpt = this.grupoRep.findById(UUID.fromString(estatisticaForm.grupoId))

        if (usuarioOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Usuário não encontrado/existe."))
        }

        if (grupoOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Grupo não encontrado/existe."))
        }

        estatistica.usuario = usuarioOpt.get()
        estatistica.grupo = grupoOpt.get()

        val estatisticaPersistida: Estatistica = this.estatisticaRep.save(estatistica)

        return ResponseEntity.status(201).body(estatisticaPersistida)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma estatística")
    @ApiResponses(value = [
        ApiResponse(responseCode = "202", description = "Retorna a estatística atualizada"),
        ApiResponse(responseCode = "404", description = "Estatística não encontrada")
    ])
    fun update(@PathVariable id: String, @RequestBody estatisticaForm: Map<String, Any>): ResponseEntity<Any> {
        val estatisticaOpt: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (estatisticaOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val estatisticaAtualizada = this.estatisticaService.atualizarEntidade(estatisticaOpt.get(), estatisticaForm)
        return ResponseEntity.status(202).body(estatisticaAtualizada)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma estatística")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Estatística deletada com sucesso"),
        ApiResponse(responseCode = "404", description = "Estatística não encontrada")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Estatistica> {
        val estatisticaOpt: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (!estatisticaOpt.isPresent) {
            return ResponseEntity.notFound().build()
        }

        this.estatisticaRep.delete(estatisticaOpt.get())
        return ResponseEntity.ok().build()
    }

}