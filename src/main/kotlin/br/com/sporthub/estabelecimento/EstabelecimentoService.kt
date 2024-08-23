package br.com.sporthub.estabelecimento

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EstabelecimentoService(repository: EstabelecimentoRepository) : GenericService<Estabelecimento>(Estabelecimento::class.java)