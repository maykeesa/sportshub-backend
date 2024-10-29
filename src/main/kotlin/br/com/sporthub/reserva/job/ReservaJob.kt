package br.com.sporthub.reserva.job

import br.com.sporthub.reserva.ReservaRepository
import lombok.extern.log4j.Log4j2
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Log4j2
@Component
class ReservaJob {

    @Autowired
    private lateinit var reservaRepository: ReservaRepository

    @Scheduled(initialDelay = 1000, fixedRate = 86400000)
    fun attReservaToInativa(){
        val reservasAtivas = this.reservaRepository.findAllByAtivaOrderByDataReservaAsc(true)

        reservasAtivas.forEach{it ->
            if(it.dataReserva < LocalDate.now()){
                it.ativa = false
                this.reservaRepository.save(it)
                log.info("Reservas atualizadas com sucesso")
            }
        }
    }

}