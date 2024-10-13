package br.com.sporthub.estabelecimento

import br.com.sporthub.estabelecimento.dto.EstabelecimentoDto
import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
import br.com.sporthub.grupo.dto.GrupoDto
import br.com.sporthub.service.UtilsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    @Operation(summary = "Listar todos os estabelecimentos")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de estabelecimentos"),
        ApiResponse(responseCode = "204", description = "Não há estabelecimentos cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<EstabelecimentoDto>> {
        val estabelecimentosPage: Page<Estabelecimento> = this.estabelecimentoRep.findAll(paginacao)
        val estabelecimentoDtoPage: Page<EstabelecimentoDto> =
            estabelecimentosPage.map { estabelecimento -> EstabelecimentoDto(estabelecimento) }

        return ResponseEntity.ok(estabelecimentoDtoPage)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um estabelecimento pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um estabelecimento"),
        ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val estabelecimento: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimento.isPresent){
            return ResponseEntity.ok(EstabelecimentoDto(estabelecimento.get()))
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Salvar um estabelecimento")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Estabelecimento salvo com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro na requisição")
    ])
    fun save(@RequestBody @Valid estabelecimentoForm: EstabelecimentoForm): ResponseEntity<EstabelecimentoDto>{
        val mapper = UtilsService.getGenericModelMapper()
        val estabelecimento: Estabelecimento = this.estabelecimentoRep.save(mapper.map(estabelecimentoForm, Estabelecimento::class.java))

        return ResponseEntity.status(201).body(EstabelecimentoDto(estabelecimento))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um estabelecimento")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o estabelecimento atualizado"),
        ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody @Valid estabelecimentoForm: Map<String, Any>): ResponseEntity<Any>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return  ResponseEntity.notFound().build()
        }

        val estabelecimentoAtualizado = this.estabelecimentoService.atualizarEntidade(estabelecimentoOpt.get(), estabelecimentoForm)
        return ResponseEntity.status(202).body(EstabelecimentoDto(estabelecimentoAtualizado as Estabelecimento))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um estabelecimento")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Estabelecimento deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void>{
        val estabelecimentoOpt: Optional<Estabelecimento> = this.estabelecimentoRep.findById(UUID.fromString(id))

        if(estabelecimentoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.estabelecimentoRep.delete(estabelecimentoOpt.get())
        return ResponseEntity.ok().build()
    }

}