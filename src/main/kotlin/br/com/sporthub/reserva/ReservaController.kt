package br.com.sporthub.reserva

import br.com.sporthub.config.security.AuthorizationService
import br.com.sporthub.grupo.Grupo
import br.com.sporthub.grupo.GrupoRespository
import br.com.sporthub.horario.Horario
import br.com.sporthub.horario.HorarioRepository
import br.com.sporthub.quadra.Quadra
import br.com.sporthub.quadra.QuadraRepository
import br.com.sporthub.reserva.dto.ReservaDto
import br.com.sporthub.reserva.form.ReservaForm
import br.com.sporthub.service.UtilsService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.List

@RestController
@RequestMapping("/reserva")
class ReservaController {

    @Autowired
    private lateinit var authService: AuthorizationService
    @Autowired
    private lateinit var reservaService: ReservaService

    @Autowired
    private lateinit var reservaRep: ReservaRepository
    @Autowired
    private lateinit var horarioRep: HorarioRepository
    @Autowired
    private lateinit var usuarioRep: UsuarioRepository
    @Autowired
    private lateinit var quadraRep: QuadraRepository
    @Autowired
    private lateinit var grupoRep: GrupoRespository

    @GetMapping
    @Operation(summary = "Listar todas as reservas")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de reservas"),
        ApiResponse(responseCode = "204", description = "Não há reservas cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["dataReserva"], direction = Sort.Direction.DESC,
        page = 0, size = 10) paginacao: Pageable
    ): ResponseEntity<Page<Reserva>> {
        val reservas: Page<Reserva> = this.reservaRep.findAll(paginacao)

        return ResponseEntity.ok(reservas)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma reserva pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma reserva"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any> {
        val reserva: Optional<Reserva> = this.reservaRep.findById(UUID.fromString(id))

        if(reserva.isPresent){
            return ResponseEntity.ok(reserva.get())
        }

        return  ResponseEntity.notFound().build()
    }

    @GetMapping("/quadra/{id}")
    @Operation(summary = "Buscar uma reserva pela quadra")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma reserva"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun getReservasByQuadra(@PathVariable id: String): ResponseEntity<Any> {
        val quadraOpt = this.quadraRep.findById(UUID.fromString(id))

        if(quadraOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Quadra não encontrado/existe."))
        }

        val reservas: List<Reserva> = this.reservaRep.findReservasByQuadraId(UUID.fromString(id))
        val reservasDto: List<ReservaDto> = reservas.map { reserva -> ReservaDto(reserva) }

        return ResponseEntity.ok(reservasDto)
    }

    @GetMapping("/grupo/{id}")
    @Operation(summary = "Buscar uma reserva pelo grupo")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma reserva"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun getReservasByGrupo(@PathVariable id: String): ResponseEntity<Any> {
        val grupoOpt = this.grupoRep.findById(UUID.fromString(id))

        if(grupoOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Grupo não encontrado/existe."))
        }

        println(id)
        val reservas: List<Reserva> = this.reservaRep.findReservasByGrupoId(UUID.fromString(id))
        println(reservas)
        val reservasDto: List<ReservaDto> = reservas.map { reserva -> ReservaDto(reserva) }

        return ResponseEntity.ok(reservasDto)
    }

    @PostMapping
    @Operation(summary = "Salvar uma reserva")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna a reserva salvo"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun save(@RequestBody @Valid reservaForm: ReservaForm): ResponseEntity<Any> {
        val mapper = UtilsService.getGenericModelMapper()
        val reserva = mapper.map(reservaForm, Reserva::class.java)

        val horarioOpt: Optional<Horario> = this.horarioRep.findById(UUID.fromString(reservaForm.horarioId))
        if(horarioOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Horário não encontrado/existe."))
        }

        if(reservaForm.grupoId != null){
            val grupoOpt: Optional<Grupo> = this.grupoRep.findById(UUID.fromString(reservaForm.grupoId))

            if(grupoOpt.isEmpty){
                return ResponseEntity.status(404).body(mapOf("error" to "Grupo não encontrado/existe."))
            }

            reserva.grupo = grupoOpt.get()
        }

        reserva.horario = horarioOpt.get()
        reserva.usuario = this.authService.getUsuarioLogado() as Usuario
        reserva.dataReserva = UtilsService.dataStringToLocalDate(reservaForm.dataReserva)
        val reservaPersistida: Reserva = this.reservaRep.save(reserva)

        return ResponseEntity.status(201).body(ReservaDto(reservaPersistida))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma reserva")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna a reserva atualizada"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    ])
    fun update(@PathVariable id: String, @RequestBody reservaForm: Map<String,Any>): ResponseEntity<Any> {
        val reservaOpt: Optional<Reserva> = this.reservaRep.findById(UUID.fromString(id))

        if (reservaOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val reservaAtualizado = this.reservaService.atualizarEntidade(reservaOpt.get(), reservaForm)
        return ResponseEntity.status(202).body(reservaAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma reserva")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Reserva deletada com sucesso"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val reservaOpt: Optional<Reserva> = this.reservaRep.findById(UUID.fromString(id))

        if(reservaOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.reservaRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Buscar uma reserva pelo ID do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma reserva"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun getReservasByUsuario(@PathVariable id: String): ResponseEntity<Any> {
        val usuarioOpt = this.usuarioRep.findById(UUID.fromString(id))

        if(usuarioOpt.isEmpty){
            return ResponseEntity.status(404).body(mapOf("error" to "Usuário não encontrado/existe."))
        }

        val reservas: List<Reserva> = this.reservaRep.findReservasByUsuarioId(UUID.fromString(id))
        val reservasDto: List<ReservaDto> = reservas.map { reserva -> ReservaDto(reserva) }

        return ResponseEntity.ok(reservasDto)
    }
}