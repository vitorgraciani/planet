package br.com.api.planet.infrastructure.integration

import br.com.api.planet.infrastructure.integration.resource.StarWarApiResource
import br.com.api.planet.infrastructure.integration.resource.StarWarsPlanetResource
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "star-wars-planet", url = "https://swapi.co", decode404 = true)
interface StarWarsPlanetClient {

    @GetMapping(value = "/api/planets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPlanets(@RequestParam ("page") pageNumber : Int) : ResponseEntity<StarWarApiResource>
}