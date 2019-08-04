package br.com.api.planet.endpoint

import br.com.api.planet.PlanetApplication
import br.com.api.planet.endpoint.resource.request.PlanetRequest
import br.com.api.planet.infrastructure.repository.PlanetRepository
import br.com.api.planet.infrastructure.repository.entity.PlanetEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [PlanetApplication::class])
@AutoConfigureMockMvc
class PlanetEndpointTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
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
        planetRepository.save(planetEntity)
    }

    @After
    fun after_test() = planetRepository.deleteAll()

    private val request = "{\"name\":\"Alderaan\",\"climate\":\"temperate\",\"terrain\":\"grasslands mountains\",\"movieAppearances\": 1}"

   @Test
   fun should_be_created(){
       mockMvc.perform(post("/v1/star_wars/planet").content(request)
               .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status().isCreated());
   }

    @Test
    fun should_be_get_planet_by_id(){
        val planetId = planetRepository.save(planetEntity).planetId
        mockMvc.perform(get("/v1/star_wars/planet/".plus(planetId))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    fun should_be_get_failed_when_id_not_found(){
        mockMvc.perform(get("/v1/star_wars/planet/2")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    fun should_be_get_planets(){
        mockMvc.perform(get("/v1/star_wars/planets")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    fun should_be_get_planet_by_name(){
        mockMvc.perform(get("/v1/star_wars/planet/name/Alderaan")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    fun should_be_failed_when_planet_by_name_not_exists(){
        mockMvc.perform(get("/v1/star_wars/planet/name/teste")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    fun should_be_deleted_planet(){
        val planetId = planetRepository.save(planetEntity).planetId
        mockMvc.perform(delete("/v1/star_wars/planet/".plus(planetId))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    fun should_be_failed_delete_planet_when_id_not_found(){
        mockMvc.perform(delete("/v1/star_wars/planet/10")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}