package br.com.sporthub.esporte

import br.com.sporthub.service.GenericService
import org.springframework.stereotype.Service

@Service
class EsporteService : GenericService<Esporte>(Esporte::class.java){

    fun getListEsportes(ids: List<String>): ArrayList<Esporte>{
        val esportesAny: ArrayList<Any> = transformarListIdToEntity(ids)
        val esportes: ArrayList<Esporte> = ArrayList(esportesAny.map { it as Esporte })
        return esportes
    }

}