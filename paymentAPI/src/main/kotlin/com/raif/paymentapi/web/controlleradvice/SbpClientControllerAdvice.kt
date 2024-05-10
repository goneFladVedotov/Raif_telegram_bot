package com.raif.paymentapi.web.controlleradvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import raiffeisen.sbp.sdk.exception.ContractViolationException
import raiffeisen.sbp.sdk.exception.SbpException
import java.io.IOException

interface SbpClientControllerAdvice {
    fun handleIO(e: IOException):ResponseEntity<*>
    fun handleSbp(e: SbpException): ResponseEntity<*>
    fun handleContractViolation(e: ContractViolationException): ResponseEntity<*>
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<*>
    fun handleException(e: Exception): ResponseEntity<*>
}