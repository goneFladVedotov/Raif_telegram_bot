package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import raiffeisen.sbp.sdk.model.`in`.RefundStatus

interface RefundService {
    fun makeRefund(refundDto: RefundDto): RefundStatus
    fun getRefundStatus(refundId: String, sbpClientDto: SbpClientDto): RefundStatus
}