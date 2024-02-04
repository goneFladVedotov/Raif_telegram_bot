package com.raif.databaseapi.web.controller

import com.raif.databaseapi.web.dto.RefundInfoDto
import org.springframework.http.ResponseEntity

interface RefundController {
    fun saveRefundInfo(refundInfoDto: RefundInfoDto): ResponseEntity<*>
    fun updateRefundStatus(refundId: String, refundStatus: String): ResponseEntity<*>
    fun getRefundInfo(refundId: String): ResponseEntity<RefundInfoDto>
    fun getAll(): ResponseEntity<List<RefundInfoDto>>
}