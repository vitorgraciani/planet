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
}