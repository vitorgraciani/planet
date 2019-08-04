package br.com.api.planet.domain

data class Planet(val planetId: Long?, val name : String?, val climate : String?, val terrain : String?, val movieAppearances : Int?) {

    constructor(name : String?, climate : String?, terrain : String?, movieAppearances : Int?) : this(null, name, climate, terrain, movieAppearances )
}