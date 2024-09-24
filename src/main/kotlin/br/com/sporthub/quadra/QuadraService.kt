package br.com.sporthub.quadra

import br.com.sporthub.esporte.Esporte
import br.com.sporthub.esporte.EsporteService
import br.com.sporthub.service.GenericService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuadraService: GenericService<Quadra>(Quadra::class.java){

    @Autowired
    private lateinit var esporteService: EsporteService

    @Autowired
    private lateinit var quadraRep: QuadraRepository

    fun modifyEsportesInQuadra(quadra: Quadra, quadraForm: Map<String, Any>, modificacao: String): Quadra{
        var esportes: List<Esporte> = ArrayList()

        if(quadraForm["esportes"] != null){
            val esportesId: List<*> = quadraForm["esportes"] as List<*>
            esportes = this.esporteService.transformarListIdToEntity(esportesId).map { it as Esporte }
        }

        if(modificacao.equals("add")){
            esportes.forEach {
                quadra.esportes.add(it)
            }
        }

        if(modificacao.equals("delete")){
            esportes.forEach{
                if(quadra.esportes.contains(it)){
                    quadra.esportes.remove(it)
                }
            }
        }

        return this.quadraRep.save(quadra)
    }
}