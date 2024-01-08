package com.raif.databaseapi.web.controlleradvice

import com.raif.databaseapi.exception.ResourceNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DatabaseControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(e: ResourceNotFoundException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ResponseEntity<*> {
        e.printStackTrace()
        return ResponseEntity.internalServerError().body(e.message)
    }
}