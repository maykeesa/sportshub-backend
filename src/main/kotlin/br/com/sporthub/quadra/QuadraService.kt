package br.com.sporthub.quadra

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class QuadraService: GenericService<Quadra>(Quadra::class.java) {
}