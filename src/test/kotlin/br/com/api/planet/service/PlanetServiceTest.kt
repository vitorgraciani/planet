package br.com.api.planet.service

import br.com.api.planet.PlanetApplication
import br.com.api.planet.domain.Planet
import br.com.api.planet.infrastructure.exception.PlanetNotFoundException
import br.com.api.planet.infrastructure.repository.PlanetRepository
import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [PlanetApplication::class])
class PlanetServiceTest {

    @Autowired
    private lateinit var planetService: PlanetService
    @MockBean
    private lateinit var planetRepository : PlanetRepository
    private val planet = Planet("Alderaan", "temperate", "grasslands, mountains", 2)
    private var planetEntity = PlanetEntity()

    @Before
    fun setup(){
        planetEntity.apply {
            planetId = 1L
            name = "Alderaan"
            climate = "temperate"
            terrain = "grasslands, mountains"
            movieAppearances = 2
        }
    }

    @Test
    fun should_be_create_planet(){
        Mockito.doReturn(planetEntity).`when`(planetRepository).save(Mockito.any())
        Assert.assertNotNull(planetService.createPlanet(planet))
    }

    @Test
    fun should_be_get_planets(){
        Mockito.doReturn(mutableListOf(planetEntity)).`when`(planetRepository).findAll()
        val planets = planetService.getPlanets()
        planets.forEach { it ->
            Assert.assertEquals(it.name, planetEntity.name)
            Assert.assertEquals(it.climate, planetEntity.climate)
            Assert.assertEquals(it.terrain, planetEntity.terrain)
            Assert.assertEquals(it.movieAppearances, planetEntity.movieAppearances)
        }
    }

    @Test
    fun should_be_get_planet_by_id(){
        Mockito.doReturn(planetEntity).`when`(planetRepository).findByPlanetId(Mockito.anyLong())
        val planet = planetService.getPlanetId(1L)
        planet.apply {
            Assert.assertEquals(name, planetEntity.name)
            Assert.assertEquals(climate, planetEntity.climate)
            Assert.assertEquals(terrain, planetEntity.terrain)
            Assert.assertEquals(movieAppearances, planetEntity.movieAppearances)
        }
    }

    @Test(expected = PlanetNotFoundException::class)
    fun should_be_failed_when_planet_not_found_by_id(){
        Mockito.doReturn(null).`when`(planetRepository).findByPlanetId(Mockito.anyLong())
        planetService.getPlanetId(1L)
    }

    @Test(expected = PlanetNotFoundException::class)
    fun should_be_failed_when_get_planet_by_name_not_found(){
        Mockito.doReturn(null).`when`(planetRepository).findPlanetByName(Mockito.anyString())
        planetService.getPlanetName("Alderaan")
    }

    @Test
    fun should_be_deleted_planet(){
        Mockito.doReturn(planetEntity).`when`(planetRepository).findByPlanetId(Mockito.anyLong())
        Mockito.doNothing().`when`(planetRepository).delete(Mockito.any())
        planetService.deletePlanet(1L)
    }

    @Test(expected = IllegalStateException::class)
    fun should_be_failed_delete_when_planet_not_found(){
        Mockito.doReturn(null).`when`(planetRepository).findByPlanetId(Mockito.anyLong())
        Mockito.doNothing().`when`(planetRepository).delete(Mockito.any())
        planetService.deletePlanet(1L)
    }
}