package br.com.api.planet.infrastructure.repository

import br.com.api.planet.PlanetApplication
import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [PlanetApplication::class])
class PlanetRepositoryTest {

    @Autowired
    private lateinit var planetRepository: PlanetRepository
    private var planetEntity = PlanetEntity()

    @Before
    fun setup() {
        planetEntity.apply {
            name = "Alderaan"
            climate = "temperate"
            terrain = "grasslands, mountains"
            movieAppearances = 2
        }
    }

    @After
    fun after_test() = planetRepository.deleteAll()

    @Test
    fun should_be_created_planet() = Assert.assertNotNull(planetRepository.save(planetEntity).planetId)

    @Test
    fun should_be_delete_planet_entity(){
        val id = planetRepository.save(planetEntity).planetId
        val recovered = id?.let { planetRepository.findByPlanetId(it)}
        recovered?.let { planetRepository.delete(it) }
        Assert.assertNull(id?.let { planetRepository.findByPlanetId(it)} ?: null)
    }

    @Test
    fun should_be_get_all_planets(){
        planetRepository.save(planetEntity)
        Assert.assertEquals(planetRepository.findAll().toList().size, 1)
    }

    @Test
    fun should_be_get_planet_by_id(){
        val planetId = planetRepository.save(planetEntity).planetId
        val recovered = planetId?.let { planetRepository.findByPlanetId(it)}
        Assert.assertNotNull(recovered)
        recovered?.apply {
            Assert.assertEquals(name, planetEntity.name)
            Assert.assertEquals(climate, planetEntity.climate)
            Assert.assertEquals(terrain, planetEntity.terrain)
            Assert.assertEquals(movieAppearances, planetEntity.movieAppearances)
        }
    }

    @Test
    fun should_be_get_planet_by_name() {
        planetRepository.save(planetEntity)
        val recovered = planetRepository.findPlanetByName("Alderaan")
        Assert.assertNotNull(recovered)
        recovered?.apply {
            Assert.assertEquals(name, planetEntity.name)
            Assert.assertEquals(climate, planetEntity.climate)
            Assert.assertEquals(terrain, planetEntity.terrain)
            Assert.assertEquals(movieAppearances, planetEntity.movieAppearances)
        }
    }


}