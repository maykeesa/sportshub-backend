package br.com.sporthub.reserva

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class ReservaService: GenericService<Reserva>(Reserva::class.java)