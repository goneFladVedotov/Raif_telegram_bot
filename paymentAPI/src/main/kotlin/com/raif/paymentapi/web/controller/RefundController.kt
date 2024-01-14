package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.RefundDto
import org.springframework.http.ResponseEntity

interface RefundController {
    fun makeRefund(refundDto: RefundDto): ResponseEntity<*>
}