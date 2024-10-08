package br.com.sporthub.usuario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UsuarioRepository : JpaRepository<Usuario,UUID>{

    fun findByEmail(email: String): Optional<Usuario>
}