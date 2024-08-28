package br.com.sporthub.horario

import br.com.sporthub.horario.form.HorarioForm
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
@RequestMapping("/horario")
class HorarioController {

    @Autowired
    private lateinit var horarioService: HorarioService
    @Autowired
    private lateinit var horarioRep: HorarioRepository

    @GetMapping
    @Operation(summary = "Listar todos os horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de horários"),
        ApiResponse(responseCode = "204", description = "Não há horário cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["diaSemana"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Horario>> {
        val horarios: Page<Horario> = this.horarioRep.findAll(paginacao)

        return ResponseEntity.ok(horarios)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um horário pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um horário"),
        ApiResponse(responseCode = "404", description = "Horário não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val horario: Optional<Horario> = this.horarioRep.findById(UUID.fromString(id))

        if(horario.isPresent){
            return ResponseEntity.ok(horario.get())
        }

        return  ResponseEntity.notFound().build()
    }


    @PostMapping
    @Operation(summary = "Salvar um horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o horário salvo"),
        ApiResponse(responseCode = "404", description = "Horário não encontrado")
    ])
    fun save(@RequestBody @Valid horarioForm: HorarioForm): ResponseEntity<Horario> {
        val horario: Horario = this.horarioRep.save(ModelMapper().map(horarioForm, Horario::class.java))

        return ResponseEntity.status(201).body(horario)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o horário atualizado"),
        ApiResponse(responseCode = "404", description = "Horário não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody horarioForm: Map<String,Any>): ResponseEntity<Any> {
        val horarioOpt: Optional<Horario> = this.horarioRep.findById(UUID.fromString(id))

        if (horarioOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val horarioAtualizado = this.horarioService.atualizarEntidade(horarioOpt.get(), horarioForm)
        return ResponseEntity.ok(horarioAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Horário deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Horário não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val horarioOpt: Optional<Horario> = this.horarioRep.findById(UUID.fromString(id))

        if(horarioOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.horarioRep.deleteById(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}