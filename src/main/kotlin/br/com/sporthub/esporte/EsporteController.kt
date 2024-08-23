package br.com.sporthub.esporte

import br.com.sporthub.esporte.form.EsporteForm
import br.com.sporthub.usuario.Usuario
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Esporte>> {
        var esporte: Page<Esporte> = this.esporteRep.findAll(paginacao)

        return ResponseEntity.ok(esporte)
    }

    @GetMapping("/{id}")
    fun getOne(id: UUID): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteRep.findById(id)

        if (!esporte.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(esporte.get())
    }

    // Corrigir, utilizar o Form
    @PostMapping()
    fun save(esporteForm: EsporteForm): ResponseEntity<Esporte> {
        var esporte: Esporte = this.esporteRep.save(ModelMapper().map(esporteForm, Esporte::class.java))

        return ResponseEntity.ok(esporte)
    }


    // Não ta fazendo nd
    @PutMapping("/{id}")
    fun update(id: UUID, esporteForm: EsporteForm): ResponseEntity<Esporte> {
        var esporte: Optional<Esporte> = this.esporteService.atualizarEntidade(id, esporteForm)

        if (!esporte.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(esporte.get())
    }


    @DeleteMapping("/{id}")
    fun delete(id: String): ResponseEntity<Esporte> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if(esporteOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.esporteRep.deleteById(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}