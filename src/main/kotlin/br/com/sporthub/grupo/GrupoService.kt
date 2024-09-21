package br.com.sporthub.grupo

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class GrupoService : GenericService<Grupo>(Grupo::class.java)