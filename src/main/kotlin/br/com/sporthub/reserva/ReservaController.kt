package br.com.sporthub.reserva

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
@RequestMapping("/reserva")
class ReservaController {

    @Autowired
    private lateinit var reservaService: ReservaService
    @Autowired
    private lateinit var reservaRep: ReservaRepository

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

    @PostMapping
    @Operation(summary = "Salvar uma reserva")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna a reserva salvo"),
        ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    ])
    fun save(@RequestBody @Valid reservaForm: ReservaForm): ResponseEntity<Reserva> {
        val reserva: Reserva = this.reservaRep.save(ModelMapper().map(reservaForm, Reserva::class.java))

        return ResponseEntity.status(201).body(reserva)
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
}