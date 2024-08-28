package br.com.sporthub.estatistica

import br.com.sporthub.estatistica.form.EstatisticaForm
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
@RequestMapping("/estatistica")
class EstatisticaController {

    @Autowired
    private lateinit var estatisticaService: EstatisticaService
    @Autowired
    private lateinit var estatisticaRep: EstatisticaRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = ["id"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Estatistica>> {
        val estatisticas: Page<Estatistica> = this.estatisticaRep.findAll(paginacao)

        return ResponseEntity.ok(estatisticas)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val estatistica: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (estatistica.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(estatistica.get())
    }

    @PostMapping
    fun save(@RequestBody @Valid estatisticaForm: EstatisticaForm): ResponseEntity<Estatistica> {
        val estatistica: Estatistica = this.estatisticaRep.save(ModelMapper().map(estatisticaForm, Estatistica::class.java))

        return ResponseEntity.status(201).body(estatistica)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, estatisticaForm: Map<String, Any>): ResponseEntity<Any> {
        val estatisticaOpt: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (estatisticaOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val estatisticaAtualizada = this.estatisticaService.atualizarEntidade(estatisticaOpt.get(), estatisticaForm)
        return ResponseEntity.status(202).body(estatisticaAtualizada)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Estatistica> {
        val estatisticaOpt: Optional<Estatistica> = this.estatisticaRep.findById(UUID.fromString(id))

        if (!estatisticaOpt.isPresent) {
            return ResponseEntity.notFound().build()
        }

        this.estatisticaRep.delete(estatisticaOpt.get())
        return ResponseEntity.ok().build()
    }

}