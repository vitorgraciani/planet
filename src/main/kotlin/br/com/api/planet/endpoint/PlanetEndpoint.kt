package br.com.api.planet.endpoint

import br.com.api.planet.domain.Planet
import br.com.api.planet.endpoint.resource.response.PlanetResponse
import br.com.api.planet.endpoint.resource.request.PlanetRequest
import br.com.api.planet.endpoint.resource.response.PlanetApiResponse
import br.com.api.planet.service.PlanetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/star_wars")
class PlanetEndpoint {

    @Autowired
    private lateinit var planetService : PlanetService

    @PostMapping("/planet")
    fun createPlanet(@RequestBody request : PlanetRequest) : ResponseEntity<Long?> {
        var planet: Planet? = null
        request.apply {
            planet = Planet(name, climate, terrain, movieAppearances)
        }
        val planetId = planetService.createPlanet(planet)
        return ResponseEntity(planetId, HttpStatus.CREATED)
    }

    @GetMapping("/api/planets")
    fun getPlanetsApi(): ResponseEntity<List<PlanetApiResponse>>{
        val planets = planetService.getSwapApiPlanets()
        val response = planets.map { PlanetApiResponse(it.name, it.climate, it.terrain, it.movieAppearances) }.toList()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/planets")
    fun getAllPlanet() : ResponseEntity<List<PlanetResponse>> {
        val planets = planetService.getPlanets()
        val response = planets.map { it -> PlanetResponse(it.planetId, it.name, it.climate, it.terrain, it.movieAppearances) }.toList()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/planet/{planetId}")
    fun getPlanet(@PathVariable ("planetId") planetId : Long) : ResponseEntity<PlanetResponse> {
        val planet = planetService.getPlanetId(planetId)
        val planetResponse = PlanetResponse(planet.planetId, planet.name, planet.climate, planet.terrain, planet.movieAppearances)
        return ResponseEntity(planetResponse, HttpStatus.OK)
    }

    @GetMapping("/planet/name/{name}")
    fun getPlanetName(@PathVariable ("name") name : String) : ResponseEntity<PlanetResponse>{
        val planet = planetService.getPlanetName(name)
        val planetResponse = PlanetResponse(planet.planetId, planet.name, planet.climate, planet.terrain, planet.movieAppearances)
        return ResponseEntity(planetResponse, HttpStatus.OK)
    }

    @DeleteMapping("/planet/{planetId}")
    fun deletePlanet(@PathVariable ("planetId") planetId : Long) : ResponseEntity<Unit> {
        planetService.deletePlanet(planetId)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }
}