package br.com.sporthub.jogo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JogoRepository: JpaRepository<Jogo, UUID>