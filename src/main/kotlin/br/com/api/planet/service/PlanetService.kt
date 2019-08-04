package br.com.api.planet.service

import br.com.api.planet.domain.Planet
import br.com.api.planet.infrastructure.exception.PlanetNotFoundException
import br.com.api.planet.infrastructure.integration.StarWarsPlanetClient
import br.com.api.planet.infrastructure.repository.PlanetRepository
import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class PlanetService {

    @Autowired
    private lateinit var starWarsPlanetClient : StarWarsPlanetClient
    @Autowired
    private lateinit var  planetRepository : PlanetRepository


    fun createPlanet(planet: Planet?) : Long?{
        var planetEntity = PlanetEntity()
        planet?.apply {
            planetEntity.name = name
            planetEntity.climate = climate
            planetEntity.terrain = terrain
            planetEntity.movieAppearances = movieAppearances
        }
        return planetRepository.save(planetEntity).planetId
    }

    fun getSwapApiPlanets(): List<Planet> {
        var pageNumber = 1
        var response = starWarsPlanetClient.getPlanets(pageNumber)
        var planets: MutableList<Planet> = mutableListOf()
        try {
            while (response.statusCode.is2xxSuccessful) {
                response.body?.results?.forEach {
                    planets.add(Planet(it.name, it.climate, it.terrain, it.films.size))
                }
                pageNumber = pageNumber.plus(1)
                response = starWarsPlanetClient.getPlanets(pageNumber)
            }
        } catch (e : Exception){
            println("finish execution")
        }
        return planets
    }

    fun getPlanets(): List<Planet> {
        return planetRepository.findAll().let{ it.map { i -> Planet(i.planetId, i.name,i.climate,i.terrain,
                i?.movieAppearances ?: 0)}.toList()}

    }

    fun getPlanetId(planetId: Long) : Planet{
        val entity = planetRepository.findByPlanetId(planetId)
        return entity?.let { Planet(it.planetId, it.name,it.climate,it.terrain,it.movieAppearances) }
                ?: throw PlanetNotFoundException("Planet with id: $planetId does not exists")
    }

    fun getPlanetName(name: String) : Planet{
        val entity = planetRepository.findPlanetByName(name)
        return entity?.let { Planet(it.planetId, it.name,it.climate,it.terrain,it.movieAppearances) }
                ?: throw PlanetNotFoundException("Planet with name: $name does not exists")
    }

    fun deletePlanet(planetId: Long) {
        val entity = planetRepository.findByPlanetId(planetId)
        entity?.let { planetRepository.delete(it) } ?: throw IllegalStateException("Error delete planet")
    }

}