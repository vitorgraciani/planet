package br.com.api.planet.infrastructure.repository.entity

import javax.persistence.*

@Entity
@Table(name ="planet")
class PlanetEntity {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "planet_id")
    var planetId : Long? = null
    var name  : String? = ""
    var climate : String? = ""
    var terrain : String? = ""
    @Column(name ="appearance_films")
    var movieAppearances : Int? = 0

    fun validateBeforeSave() {
        fun <T> validate(attr: T, name: String) {
            println("starting validation $name")
            if (attr == null) {
                throw IllegalArgumentException("O campo $name esta nulo, por isso nao pode ser salvo")
            }
        }
        validate(name, "name")
        validate(climate, "climate")
        validate(terrain, "terrain")
        validate(movieAppearances, "movie appearances")

    }

    fun validate(planetEntity : PlanetEntity){
        planetEntity.validateBeforeSave()
    }
}