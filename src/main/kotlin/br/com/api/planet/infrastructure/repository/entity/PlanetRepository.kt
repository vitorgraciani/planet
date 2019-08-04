package br.com.api.planet.infrastructure.repository

import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlanetRepository : CrudRepository<PlanetEntity, Long> {

    fun findPlanetByName(name : String): PlanetEntity?
    fun findByPlanetId(planetId: Long) : PlanetEntity?
}