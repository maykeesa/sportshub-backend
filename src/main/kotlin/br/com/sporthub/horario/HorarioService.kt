package br.com.sporthub.horario

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class HorarioService: GenericService<Horario>(Horario::class.java) {
}