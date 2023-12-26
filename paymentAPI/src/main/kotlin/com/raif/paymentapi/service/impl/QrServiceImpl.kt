package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.service.QrService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.out.QRDynamic

@Service
class QrServiceImpl: QrService {
    private val sbpMerchantId = "MA622976"
    private val secretKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQTYyMjk3NiIsImp0aSI6ImM5MTBjNGU4LTRhZmMtNDBlMS04ZGU3LWVlODg2N2JiOGU3NCJ9.rnPFEsixy9Wr4GhxT9D9s8dlBg5dRKWMLPfxl48oHAo"
    override fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val qrCode = QRDynamic(qrDynamicDto.order, qrDynamicDto.amount)
        return sbpClient.registerQR(qrCode)
    }
}