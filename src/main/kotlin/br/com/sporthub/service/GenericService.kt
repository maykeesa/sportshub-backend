package br.com.sporthub.service

import org.springframework.beans.BeanUtils
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.*


open class GenericService<T>(
    private val repository: JpaRepository<T, UUID>
) where T : Any {

    fun <F : Any> atualizarEntidade(id: UUID, form: F, excludeProperties: Array<String> = arrayOf("id")): Optional<T> {
        val entidadeOpt: Optional<T> = repository.findById(id)

        if (entidadeOpt.isPresent) {
            val entidade = entidadeOpt.get()
            // Use the spread operator (*) to pass the array as vararg
            BeanUtils.copyProperties(form, entidade, *excludeProperties)

            return Optional.of(repository.save(entidade))
        }

        return Optional.empty()
    }
}