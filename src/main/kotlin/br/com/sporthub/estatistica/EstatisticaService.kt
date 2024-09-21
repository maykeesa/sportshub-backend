package br.com.sporthub.estatistica

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EstatisticaService : GenericService<Estatistica>(Estatistica::class.java)