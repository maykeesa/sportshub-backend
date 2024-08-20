package br.com.sporthub.esporte

import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @GetMapping()
    @Operation(summary = "Listar todos os esportes")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de esportes"),
        ApiResponse(responseCode = "204", description = "Não há esportes cadastrados")
    ])
    fun getAllEsportes(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Esporte>> {
            var esporte: Page<Esporte> = this.esporteRep.findAll(paginacao)

            if (!esporte.isEmpty) {
                return ResponseEntity.ok(esporte);
            }

            return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um esporte pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um esporte"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun getOneEsporte(id: UUID): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (esporte.isPresent) {
            return ResponseEntity.ok(esporte.get())
        }

        return ResponseEntity.notFound().build()
    }

    @PostMapping()
    @Operation(summary = "Cadastrar um novo esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Esporte cadastrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao cadastrar esporte")
    ])
    fun saveEsporte(esporte: Esporte): ResponseEntity<Esporte> {
        var esporte: Esporte = this.esporteRep.save(esporte)

        if (esporte == null) {
            return ResponseEntity.status(400).build()
        }

        return ResponseEntity.status(201).body(esporte)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Esporte deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun deleteEsportes(id: UUID): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (esporte.isPresent) {
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um esporte")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Esporte atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Esporte não encontrado")
    ])
    fun updateEsportes(id: UUID, esporte: Esporte): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (esporte.isPresent) {
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }


}