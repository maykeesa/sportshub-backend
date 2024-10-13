package br.com.sporthub.jogo

import br.com.sporthub.jogo.form.JogoForm
import br.com.sporthub.reserva.Reserva
import br.com.sporthub.reserva.ReservaRepository
import br.com.sporthub.reserva.ReservaService
import br.com.sporthub.reserva.form.ReservaForm
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
@RequestMapping("/jogo")
class JogoController {

    @Autowired
    private lateinit var jogoService: JogoService
    @Autowired
    private lateinit var jogoRepository: JogoRepository

    @GetMapping
    @Operation(summary = "Listar todos os jogos")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de jogos"),
        ApiResponse(responseCode = "204", description = "Não há jogos cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["torneio"], direction = Sort.Direction.DESC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Jogo>> {
        val jogos: Page<Jogo> = this.jogoRepository.findAll(paginacao)

        return ResponseEntity.ok(jogos)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um jogo pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um jogo"),
        ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val jogo: Optional<Jogo> = this.jogoRepository.findById(UUID.fromString(id))

        if(jogo.isEmpty){
            return  ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(jogo.get())
    }

    @PostMapping
    @Operation(summary = "Salvar um jogo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Jogo salvo com sucesso")
    ])
    fun save(@RequestBody @Valid jogoForm: JogoForm): ResponseEntity<Jogo> {
        val jogo: Jogo = this.jogoRepository.save(ModelMapper().map(jogoForm, Jogo::class.java))

        return ResponseEntity.status(201).body(jogo)
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um jogo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o jogo atualizado"),
        ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody jogoForm: Map<String,Any>): ResponseEntity<Any> {
        val jogoOpt: Optional<Jogo> = this.jogoRepository.findById(UUID.fromString(id))

        if (jogoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val jogoAtualizado = this.jogoService.atualizarEntidade(jogoOpt.get(), jogoForm)
        return ResponseEntity.status(202).body(jogoAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um jogo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Jogo deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val reservaOpt: Optional<Jogo> = this.jogoRepository.findById(UUID.fromString(id))

        if(reservaOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.jogoRepository.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
    
}