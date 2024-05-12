package com.raif.botConstructors.services

import com.raif.botConstructors.models.QR
import com.raif.botConstructors.models.RefundStatus
import com.raif.botConstructors.models.dto.RefundDto

interface QRCodeService {
    fun createQR(amount: Double, orderId: String): QR
    fun getQR(qrId: String): QR
    fun updateAll()

    fun refund(refundDto: RefundDto): RefundStatus
}