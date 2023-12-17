package com.raif.paymentapi.controlleradvice

import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.exception.ContractViolationException
import raiffeisen.sbp.sdk.exception.SbpException
import java.io.IOException

interface SbpClientControllerAdvice {
    fun handleIO(e: IOException):ResponseEntity<*>
    fun handleSbp(e: SbpException): ResponseEntity<*>
    fun handleContractViolation(e: ContractViolationException): ResponseEntity<*>
}