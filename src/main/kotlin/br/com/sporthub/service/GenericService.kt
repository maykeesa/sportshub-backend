package br.com.sporthub.service

import br.com.sporthub.usuario.Usuario
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import java.util.UUID

open class GenericService<T: Any>(private val entityType: Class<T>) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    open fun atualizarEntidade(entidade: Any, form: Map<String, Any>): Any {
        form.forEach{ (key, value) ->
            val field = entidade::class.java.getDeclaredField(key)
            field.isAccessible = true
            field.set(entidade, value)
        }

        this.entityManager.merge(entidade)
        return entidade
    }

    @Transactional
    open fun transformarListIdToEntity(ids: ArrayList<String>): ArrayList<Any>{
        val entidades: ArrayList<Any> = ArrayList()

        ids.forEach{ i ->
            entidades.add(this.entityManager.find(entityType, UUID.fromString(i)))
        }

        return entidades
    }
}
