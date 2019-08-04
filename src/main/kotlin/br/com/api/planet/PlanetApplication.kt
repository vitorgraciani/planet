package br.com.api.planet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients


@SpringBootApplication
@EnableFeignClients
@EnableCaching
class PlanetApplication

fun main(args: Array<String>) {
	runApplication<PlanetApplication>(*args)
}
