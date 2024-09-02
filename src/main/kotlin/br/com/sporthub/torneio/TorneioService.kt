package br.com.sporthub.torneio

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class TorneioService: GenericService<Torneio>(Torneio::class.java) {
}