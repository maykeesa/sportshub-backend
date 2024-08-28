package br.com.sporthub.quadra

import br.com.sporthub.quadra.form.QuadraForm
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/quadra")
class QuadraController {

    @Autowired
    private lateinit var quadraService: QuadraService
    @Autowired
    private lateinit var quadraRep: QuadraRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<List<Quadra>> {
        val quadras: List<Quadra> = this.quadraRep.findAll()

        return ResponseEntity.ok(quadras)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Quadra> {
        val quadra: Optional<Quadra> = this.quadraRep.findById(UUID.fromString(id))

        if (quadra.isPresent) {
            return ResponseEntity.ok(quadra.get())
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun save(@RequestBody @Valid quadraForm: QuadraForm): ResponseEntity<Quadra> {
        val quadra: Quadra = this.quadraRep.save(ModelMapper().map(quadraForm, Quadra::class.java))

        return ResponseEntity.status(201).body(quadra)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody quadraForm: Map<String, Any>): ResponseEntity<Any> {
        val quadraOpt: Optional<Quadra> = this.quadraRep.findById(UUID.fromString(id))

        if (quadraOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val quadraAtualizada = quadraService.atualizarEntidade(quadraOpt.get(), quadraForm)
        return ResponseEntity.status(202).body(quadraAtualizada)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Any> {
        val quadraOpt: Optional<Quadra> = quadraRep.findById(UUID.fromString(id))

        if (quadraOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        quadraRep.delete(quadraOpt.get())
        return ResponseEntity.ok().build()
    }
}