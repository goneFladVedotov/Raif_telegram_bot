package com.raif.databaseapi.web.controller

import com.raif.databaseapi.domain.RefundInfo
import org.springframework.http.ResponseEntity

interface RefundController {
    fun saveRefundInfo(refundInfo: RefundInfo): ResponseEntity<*>
    fun updateRefundStatus(refundId: String, refundStatus: String): ResponseEntity<*>
    fun getRefundInfo(refundId: String): ResponseEntity<RefundInfo>
}