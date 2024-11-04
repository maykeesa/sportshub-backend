package br.com.sporthub.jogo

import br.com.sporthub.jogo.dto.JogoDto
import br.com.sporthub.jogo.enums.StatusEnum
import br.com.sporthub.jogo.form.JogoForm
import br.com.sporthub.service.UtilsService
import br.com.sporthub.torneio.Torneio
import br.com.sporthub.torneio.TorneioRepository
import jakarta.validation.Valid
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
    private lateinit var jogoRep: JogoRepository
    @Autowired
    private lateinit var torneioRep: TorneioRepository

    @GetMapping
    fun getAll(@PageableDefault(sort = ["torneio"], direction = Sort.Direction.DESC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Jogo>> {
        val jogos: Page<Jogo> = this.jogoRep.findAll(paginacao)

        return ResponseEntity.ok(jogos)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val jogo: Optional<Jogo> = this.jogoRep.findById(UUID.fromString(id))

        if(jogo.isEmpty){
            return  ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(jogo.get())
    }

    @PostMapping
    fun save(@RequestBody @Valid jogoForm: JogoForm): ResponseEntity<Any> {
        val mapper = UtilsService.getGenericModelMapper()
        val jogo: Jogo = mapper.map(jogoForm, Jogo::class.java)
        val torneioOpt: Optional<Torneio> = this.torneioRep.findById(UUID.fromString(jogoForm.torneioId))

        if(torneioOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Torneio não encontrado/existe."))
        }

        jogo.torneio = torneioOpt.get()
        jogo.status = StatusEnum.AGENDADO
        val jogoPersistido: Jogo = this.jogoRep.save(jogo)

        return ResponseEntity.status(201).body(JogoDto(jogoPersistido))
    }


    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody jogoForm: Map<String,Any>): ResponseEntity<Any> {
        val jogoOpt: Optional<Jogo> = this.jogoRep.findById(UUID.fromString(id))

        if (jogoOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val jogoAtualizado = this.jogoService.atualizarEntidade(jogoOpt.get(), jogoForm)
        return ResponseEntity.status(202).body(jogoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val reservaOpt: Optional<Jogo> = this.jogoRep.findById(UUID.fromString(id))

        if(reservaOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.jogoRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }
    
}