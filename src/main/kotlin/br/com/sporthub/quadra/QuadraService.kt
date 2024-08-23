package br.com.sporthub.quadra

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class QuadraService(repository: QuadraRepository) : GenericService<Quadra>(Quadra::class.java) {
}