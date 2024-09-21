package br.com.sporthub.grupo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GrupoRespository : JpaRepository<Grupo, UUID>