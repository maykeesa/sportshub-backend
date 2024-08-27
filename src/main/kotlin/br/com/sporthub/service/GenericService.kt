package br.com.sporthub.service

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional

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

        entityManager.merge(entidade)
        return entidade
    }
}
