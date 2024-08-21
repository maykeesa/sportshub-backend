package br.com.sporthub.estabelecimento



import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.form.UsuarioForm
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
@RequestMapping("/Estabelencimento")
class EstabelecimentoController {

    @Autowired
    private lateinit var estabelecimentoService: EstabelecimentoService
    @Autowired
    private lateinit var estabelecimentoRep: EstabelecimentoRepository

    @GetMapping()
    fun getAll(@PageableDefault(sort = arrayOf("nome"), direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Estabelecimento>> {
        var estabelecimentos: Page<Estabelecimento> = this.estabelecimentoRep.findAll(paginacao)

        return ResponseEntity.ok(estabelecimentos)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        var estabelecimento: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimento.isPresent){
            return ResponseEntity.ok(estabelecimento.get())
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping()
    fun save(@RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<Estabelecimento>{
        var estabelecimento: Estabelecimento = this.estabelecimentoRep.save(ModelMapper().map(estabelecimentoForm, Estabelecimento::class.java))

        return ResponseEntity.ok(estabelecimento)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<Estabelecimento>{
        val estabelecimentoOpt: Optional<Estabelecimento> = estabelecimentoService.atualizarEstabelecimento(id, estabelecimentoForm)

        if(!estabelecimentoOpt.isPresent){
            return  ResponseEntity.notFound().build()
        }

        var estabelecimento = estabelecimentoRep.save(estabelecimentoOpt.get())
        return ResponseEntity.ok(estabelecimento)

    }

    @DeleteMapping
    fun delete(@PathVariable id: String): ResponseEntity<Estabelecimento>{
        var estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(!estabelecimentoOpt.isPresent){
            return ResponseEntity.noContent().build()
        }

        this.estabelecimentoRep.delete(estabelecimentoOpt.get())
        return ResponseEntity.ok().build()

    }

}