package br.com.sporthub.estabelecimento

import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
import br.com.sporthub.grupo.dto.GrupoDto
import br.com.sporthub.service.UtilsService
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
@RequestMapping("/estabelecimento")
class EstabelecimentoController {

    @Autowired
    private lateinit var estabelecimentoService: EstabelecimentoService
    @Autowired
    private lateinit var estabelecimentoRep: EstabelecimentoRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<EstabelecimentoDto>> {
        val estabelecimentosPage: Page<Estabelecimento> = this.estabelecimentoRep.findAll(paginacao)
        val estabelecimentoDtoPage: Page<EstabelecimentoDto> =
            estabelecimentosPage.map { estabelecimento -> EstabelecimentoDto(estabelecimento) }

        return ResponseEntity.ok(estabelecimentoDtoPage)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val estabelecimento: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimento.isPresent){
            return ResponseEntity.ok(EstabelecimentoDto(estabelecimento.get()))
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    fun save(@RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<EstabelecimentoDto>{
        val mapper = UtilsService.getGenericModelMapper()
        val estabelecimento: Estabelecimento = this.estabelecimentoRep.save(mapper.map(estabelecimentoForm, Estabelecimento::class.java))

        return ResponseEntity.status(201).body(EstabelecimentoDto(estabelecimento))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody @Valid estabelecimentoForm: Map<String, Any>): ResponseEntity<Any>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return  ResponseEntity.notFound().build()
        }

        val estabelecimentoAtualizado = this.estabelecimentoService.atualizarEntidade(estabelecimentoOpt.get(), estabelecimentoForm)
        return ResponseEntity.status(202).body(EstabelecimentoDto(estabelecimentoAtualizado as Estabelecimento))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.estabelecimentoRep.delete(estabelecimentoOpt.get())
        return ResponseEntity.ok().build()
    }

}