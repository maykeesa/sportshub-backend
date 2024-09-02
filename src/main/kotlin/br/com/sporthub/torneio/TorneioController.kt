package br.com.sporthub.torneio

import br.com.sporthub.torneio.form.TorneioForm
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
@RequestMapping("/torneio")
class TorneioController {

    @Autowired
    private lateinit var torneioRep: TorneioRepository

    @Autowired
    private lateinit var torneioService: TorneioService

    @GetMapping
    fun getAll(@PageableDefault(sort = ["dataCriacao"], direction = Sort.Direction.DESC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Torneio>> {
        val torneios: Page<Torneio> = this.torneioRep.findAll(paginacao)

        return ResponseEntity.ok(torneios)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String) : ResponseEntity<Torneio> {
        val torneio:Optional<Torneio> = this.torneioRep.findById(UUID.fromString(id))

        if (torneio.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(torneio.get())
    }

    @PostMapping
    fun save(@RequestBody @Valid torneioForm: TorneioForm) : ResponseEntity<Torneio> {
        val torneio: Torneio = this.torneioRep.save(ModelMapper().map(torneioForm, Torneio::class.java))

        return ResponseEntity.status(201).body(torneio)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id:String, @RequestBody torneioForm: Map<String,Any>) : ResponseEntity<Any> {
        val torneioOpt: Optional<Torneio> = this.torneioRep.findById(UUID.fromString(id))

        if (torneioOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val torneioAtualizado  = this.torneioService.atualizarEntidade(torneioOpt.get(),torneioForm)

        return ResponseEntity.status(202).body(torneioAtualizado)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:String) : ResponseEntity<Void> {
        val torneioOpt = this.torneioRep.findById(UUID.fromString(id))

        if (torneioOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        this.torneioRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
}