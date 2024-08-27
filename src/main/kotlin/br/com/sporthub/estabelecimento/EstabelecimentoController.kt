package br.com.sporthub.estabelecimento

import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
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
@RequestMapping("/estabelencimento")
class EstabelecimentoController {

    @Autowired
    private lateinit var estabelecimentoService: EstabelecimentoService
    @Autowired
    private lateinit var estabelecimentoRep: EstabelecimentoRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Estabelecimento>> {
        val estabelecimentos: Page<Estabelecimento> = this.estabelecimentoRep.findAll(paginacao)

        return ResponseEntity.ok(estabelecimentos)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val estabelecimento: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimento.isPresent){
            return ResponseEntity.ok(estabelecimento.get())
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    fun save(@RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<Estabelecimento>{
        val estabelecimento: Estabelecimento = this.estabelecimentoRep.save(ModelMapper().map(estabelecimentoForm, Estabelecimento::class.java))

        return ResponseEntity.ok(estabelecimento)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody @Valid estabelecimentoForm: Map<String, Any>): ResponseEntity<Any>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return  ResponseEntity.notFound().build()
        }

        val estabelecimentoAtualizado = this.estabelecimentoService.atualizarEntidade(estabelecimentoOpt.get(), estabelecimentoForm)
        return ResponseEntity.ok(estabelecimentoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Estabelecimento>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.estabelecimentoRep.delete(estabelecimentoOpt.get())
        return ResponseEntity.ok().build()
    }

}