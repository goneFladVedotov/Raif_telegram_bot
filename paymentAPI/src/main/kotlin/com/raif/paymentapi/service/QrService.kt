package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.QrDynamicDto
import raiffeisen.sbp.sdk.model.`in`.QRUrl

interface QrService {
    fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl
}