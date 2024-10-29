package br.com.sporthub

import br.com.sporthub.reserva.Reserva
import br.com.sporthub.reserva.ReservaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.UUID

@EnableScheduling
@SpringBootApplication
class SporthubBackendApplication

fun main(args: Array<String>) {
	runApplication<SporthubBackendApplication>(*args)

}
