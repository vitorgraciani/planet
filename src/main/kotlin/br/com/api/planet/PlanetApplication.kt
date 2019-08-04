package br.com.api.planet

import br.com.api.planet.infrastructure.repository.PlanetRepository
import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableFeignClients
class PlanetApplication

   @Bean
    fun init(planetRepository: PlanetRepository) = CommandLineRunner {

	   planetRepository.save(PlanetEntity())
	}

fun main(args: Array<String>) {
	runApplication<PlanetApplication>(*args)
}
