package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.RefundService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("payment-api/v1/refund")
class RefundControllerImpl(
    private val refundService: RefundService
) : RefundController {
    override fun makeRefund(refundDto: RefundDto): ResponseEntity<*> {
        return ResponseEntity.ok(refundService.makeRefund(refundDto))
    }
}