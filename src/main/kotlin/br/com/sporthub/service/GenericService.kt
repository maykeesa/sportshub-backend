package br.com.sporthub.service

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.beans.BeanUtils
import java.util.*

open class GenericService<T : Any>(private val entityType: Class<T>) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    open fun <F : Any> atualizarEntidade(id: UUID, form: F, excludeProperties: Array<String> = arrayOf("id")): Optional<T> {
        val entidade = entityManager.find(entityType, id)

        return if (entidade != null) {
            BeanUtils.copyProperties(form, entidade, *excludeProperties)
            entityManager.merge(entidade)
            Optional.of(entidade)
        } else {
            Optional.empty()
        }
    }
}