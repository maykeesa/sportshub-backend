package br.com.sporthub.estatistica

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EstatisticaService(repository: EstatisticaRepository) : GenericService<Estatistica>(Estatistica::class.java) {
}