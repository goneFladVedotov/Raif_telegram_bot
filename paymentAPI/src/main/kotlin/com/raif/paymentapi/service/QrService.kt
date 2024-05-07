package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import raiffeisen.sbp.sdk.model.`in`.PaymentInfo
import raiffeisen.sbp.sdk.model.`in`.QRUrl

interface QrService {
    fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl
    fun registerStaticQr(qrStaticDto: QrStaticDto): QRUrl
    fun registerVariableQr(): QRUrl
    fun getQrInfo(qrId: String): QRUrl
    fun getPaymentInfo(qrId: String): PaymentInfo
    fun checkQrExpirationDateTime()
}