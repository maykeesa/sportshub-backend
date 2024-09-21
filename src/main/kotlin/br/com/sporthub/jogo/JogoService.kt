package br.com.sporthub.jogo

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class JogoService: GenericService<Jogo>(Jogo::class.java)