package br.com.sporthub.horario

import br.com.sporthub.grupo.dto.GrupoDto
import br.com.sporthub.horario.dto.HorarioDto
import br.com.sporthub.horario.form.HorarioForm
import br.com.sporthub.quadra.Quadra
import br.com.sporthub.quadra.QuadraRepository
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
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.Duration

@RestController
@RequestMapping("/horario")
class HorarioController {

    @Autowired
    private lateinit var horarioService: HorarioService

    @Autowired
    private lateinit var horarioRep: HorarioRepository
    @Autowired
    private lateinit var quadraRep: QuadraRepository

    @GetMapping
    @Operation(summary = "Listar todos os horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de horários"),
        ApiResponse(responseCode = "204", description = "Não há horário cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["diaSemana"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<HorarioDto>> {
        val horariosPage: Page<Horario> = this.horarioRep.findAll(paginacao)
        val horariosDtoPage: Page<HorarioDto> = horariosPage.map { horario -> HorarioDto(horario) }

        return ResponseEntity.ok(horariosDtoPage)
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
            return ResponseEntity.ok(HorarioDto(horario.get()))
        }

        return  ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Salvar um horário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o horário salvo"),
        ApiResponse(responseCode = "404", description = "Horário não encontrado")
    ])
    fun save(@RequestBody @Valid horarioForm: HorarioForm): ResponseEntity<Any> {
        val mapper = UtilsService.getGenericModelMapper()

        val horario = mapper.map(horarioForm, Horario::class.java)
        val horarioInicio: LocalTime = UtilsService.timeStringToLocalTime(horarioForm.horarioInicio)
        val horarioFim: LocalTime = UtilsService.timeStringToLocalTime(horarioForm.horarioFim)

        val quadraOpt = this.quadraRep.findById(UUID.fromString(horarioForm.quadraId))

        if(quadraOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Quadra não existe."))
        }

        if(horarioFim.isBefore(horarioInicio)){
            return ResponseEntity.status(404).body(mapOf("error" to "O horário final tem que ser maior que o inicial."))
        }

        horario.horarioInicio = horarioInicio
        horario.horarioFim = horarioFim
        horario.duracao = horarioFim.minusHours(horarioInicio.hour.toLong())
            .minusMinutes(horarioInicio.minute.toLong())

        horario.quadra = quadraOpt.get()

        val horarioPersistido: Horario = this.horarioRep.save(horario)

        return ResponseEntity.status(201).body(HorarioDto(horarioPersistido))
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
        return ResponseEntity.status(202).body(HorarioDto(horarioAtualizado as Horario))
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
        return ResponseEntity.ok().build()
    }
}