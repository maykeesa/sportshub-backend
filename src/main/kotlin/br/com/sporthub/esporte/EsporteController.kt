package br.com.sporthub.esporte

import br.com.sporthub.esporte.form.EsporteForm
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
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Esporte>> {
        val esporte: Page<Esporte> = this.esporteRep.findAll(paginacao)

        return ResponseEntity.ok(esporte)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Esporte> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if (esporteOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(esporteOpt.get())
    }

    @PostMapping
    fun save(@RequestBody @Valid esporteForm: EsporteForm): ResponseEntity<Esporte> {
        val esporte: Esporte = this.esporteRep.save(ModelMapper().map(esporteForm, Esporte::class.java))

        return ResponseEntity.status(201).body(esporte)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody esporteForm: Map<String, Any>): ResponseEntity<Any> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if (esporteOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val esporteAtualizado = this.esporteService.atualizarEntidade(esporteOpt.get(), esporteForm)
        return ResponseEntity.status(202).body(esporteAtualizado)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Esporte> {
        val esporteOpt: Optional<Esporte> = this.esporteRep.findById(UUID.fromString(id))

        if(esporteOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.esporteRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
}