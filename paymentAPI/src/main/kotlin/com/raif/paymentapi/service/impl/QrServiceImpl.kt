package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.QrDto
import com.raif.paymentapi.service.QrService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.out.QRDynamic
import raiffeisen.sbp.sdk.util.QRUtil
import java.math.BigDecimal

@Service
class QrServiceImpl: QrService {
    override fun registerQr(qrDto: QrDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrDto.sbpMerchantId, qrDto.secretKey)
        val qrCode = QRDynamic(qrDto.order, BigDecimal(qrDto.amount))
        val response = sbpClient.registerQR(qrCode)
        return response
    }
}