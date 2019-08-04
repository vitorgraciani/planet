package br.com.api.planet.endpoint.handler

import br.com.api.planet.infrastructure.exception.PlanetNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class PlanetControllerAdvice {
    @ExceptionHandler(value = [(PlanetNotFoundException::class)])
    fun planetNotfound(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(HttpStatus.NOT_FOUND.name, ex.message!!)
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(IllegalStateException::class)])
    fun badRequest(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(HttpStatus.BAD_REQUEST.name, ex.message!!)
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(Exception::class)])
    fun handleUserAlreadyExists(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name, ex.message!!)
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    data class ErrorResponse (val code : String, val description : String)
}