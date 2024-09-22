package br.com.sporthub.quadra

import br.com.sporthub.esporte.Esporte
import br.com.sporthub.esporte.EsporteRepository
import br.com.sporthub.esporte.EsporteService
import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.estabelecimento.EstabelecimentoRepository
import br.com.sporthub.quadra.dto.QuadraDto
import br.com.sporthub.quadra.form.QuadraForm
import br.com.sporthub.reserva.ReservaRepository
import br.com.sporthub.service.UtilsService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/quadra")
class QuadraController {

    @Autowired
    private lateinit var quadraService: QuadraService
    @Autowired
    private lateinit var esporteService: EsporteService

    @Autowired
    private lateinit var quadraRep: QuadraRepository
    @Autowired
    private lateinit var estabelecimentoRep: EstabelecimentoRepository
    @Autowired
    private lateinit var reservaRep: ReservaRepository

    /*

    @GetMapping
    fun getAll(@PageableDefault(sort = arrayOf("id"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<QuadraDto>> {
        val quadras: Page<Quadra> = this.quadraRep.findAll(paginacao)
        val quadrasDtoPage: Page<QuadraDto> = quadras.map { quadra -> QuadraDto(quadra) }

        return ResponseEntity.ok(quadrasDtoPage)
    }
     */

    @GetMapping
    fun getAll(): ResponseEntity<List<QuadraDto>> {
        val quadras: List<Quadra> = this.quadraRep.findAll()
        val quadrasDto: List<QuadraDto> = quadras.map { quadra -> QuadraDto(quadra, false) }

        return ResponseEntity.ok(quadrasDto)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<QuadraDto> {
        val quadra: Optional<Quadra> = this.quadraRep.findById(UUID.fromString(id))

        if (quadra.isPresent) {
            return ResponseEntity.ok(QuadraDto(quadra.get()))
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/estabelecimento/{id}")
    fun getQuadrasByEstabelecimento(@PathVariable id: String): ResponseEntity<List<QuadraDto>> {
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if (estabelecimentoOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val quadras: List<Quadra> = this.quadraRep.findByEstabelecimento_Id(estabelecimentoOpt.get().id)
        val quadrasDto: List<QuadraDto> = quadras.map { quadra -> QuadraDto(quadra) }

        return ResponseEntity.ok(quadrasDto)
    }

    @PostMapping
    fun save(@RequestBody @Valid quadraForm: QuadraForm): ResponseEntity<Any> {
        val mapper = UtilsService.getGenericModelMapper()

        var quadra = mapper.map(quadraForm, Quadra::class.java)
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(quadraForm.estabelecimentoId))
        val esportes: List<Esporte> = this.esporteService.getListEsportes(quadraForm.esportes)

        if(estabelecimentoOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Estabelecimento não encontrado/existe."))
        }

        quadra.estabelecimento = estabelecimentoOpt.get()
        quadra.esportes = esportes
        quadra.horarios = ArrayList()
        quadra.id = null

        quadra = this.quadraRep.save(quadra)

        return ResponseEntity.status(201).body(QuadraDto(quadra, false))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody quadraForm: Map<String, Any>): ResponseEntity<Any> {
        val quadraOpt: Optional<Quadra> = this.quadraRep.findById(UUID.fromString(id))

        if (quadraOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val quadraAtualizada = quadraService.atualizarEntidade(quadraOpt.get(), quadraForm)
        return ResponseEntity.status(202).body(QuadraDto(quadraAtualizada as Quadra))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Any> {
        print("salvesalve ")
        val quadraOpt: Optional<Quadra> = quadraRep.findById(UUID.fromString(id))

        if (quadraOpt.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        quadraRep.delete(quadraOpt.get())
        return ResponseEntity.ok().build()
    }
}