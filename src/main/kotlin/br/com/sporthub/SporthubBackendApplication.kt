package br.com.sporthub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SporthubBackendApplication

fun main(args: Array<String>) {
	runApplication<SporthubBackendApplication>(*args)
}
