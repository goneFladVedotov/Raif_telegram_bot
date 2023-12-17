package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.QrDto
import raiffeisen.sbp.sdk.model.`in`.QRUrl

interface QrService {
    fun registerQr(qrDto: QrDto): QRUrl
}