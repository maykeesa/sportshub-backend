package br.com.sporthub.esporte

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EsporteService : GenericService<Esporte>(Esporte::class.java)