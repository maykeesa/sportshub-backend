package br.com.sporthub.esporte

import br.com.sporthub.estabelecimento.Estabelecimento
import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EsporteService(repository: EsporteRepository) : GenericService<Esporte>(Esporte::class.java)