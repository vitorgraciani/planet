package br.com.api.planet.infrastructure.integration.resource


data class StarWarApiResource (val next : String?, val results : List<StarWarsPlanetResource>)

data class StarWarsPlanetResource (val climate : String, val name : String, val terrain : String, val films : List<String>)