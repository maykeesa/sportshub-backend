package br.com.sporthub.estatistica

import br.com.sporthub.estabelecimento.Estabelecimento
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
    private lateinit var estatisticaRepository: EstatisticaRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = arrayOf("id"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Estatistica>> {
        var estatisticas: Page<Estatistica> = this.estatisticaRepository.findAll(paginacao)

        return ResponseEntity.ok(estatisticas)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        var estatistica: Optional<Estatistica> = this.estatisticaRepository.findById(UUID.fromString(id))

        if (!estatistica.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(estatistica.get())
    }

    @PostMapping
    fun save(estatistica: Estatistica): ResponseEntity<Estatistica> {
        var estatistica: Estatistica = this.estatisticaRepository.save(estatistica)

        return ResponseEntity.ok(estatistica)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, estatistica: Estatistica): ResponseEntity<Estatistica> {
        val estatisticaOpt: Optional<Estatistica> = estatisticaService.atualizarEntidade(id, estatistica)

        if (estatisticaOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        var estatistica = estatisticaRepository.save(estatisticaOpt.get())
        return ResponseEntity.ok(estatistica)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Estatistica> {
        var estatisticaOpt: Optional<Estatistica> = this.estatisticaRepository.findById(UUID.fromString(id))

        if (!estatisticaOpt.isPresent) {
            return ResponseEntity.notFound().build()
        }

        this.estatisticaRepository.delete(estatisticaOpt.get())
        return ResponseEntity.ok().build()
    }

}