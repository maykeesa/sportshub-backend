package br.com.sporthub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SporthubBackendApplication

fun main(args: Array<String>) {
	runApplication<SporthubBackendApplication>(*args)

}
