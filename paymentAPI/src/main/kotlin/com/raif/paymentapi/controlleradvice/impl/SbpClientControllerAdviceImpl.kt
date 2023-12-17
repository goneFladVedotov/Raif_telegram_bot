package com.raif.paymentapi.controlleradvice.impl

import com.raif.paymentapi.controlleradvice.SbpClientControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import raiffeisen.sbp.sdk.exception.ContractViolationException
import raiffeisen.sbp.sdk.exception.SbpException
import java.io.IOException

@RestControllerAdvice
class SbpClientControllerAdviceImpl: SbpClientControllerAdvice {
    @ExceptionHandler(IOException::class)
    override fun handleIO(e: IOException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(SbpException::class)
    override fun handleSbp(e: SbpException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(ContractViolationException::class)
    override fun handleContractViolation(e: ContractViolationException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }
}