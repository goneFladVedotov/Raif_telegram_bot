package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.RefundDto
import raiffeisen.sbp.sdk.model.`in`.RefundStatus

interface RefundService {
    fun makeRefund(refundDto: RefundDto): RefundStatus
    fun getRefundStatus(refundId: String): RefundStatus
    fun checkRefundStatus()
}