package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.service.QrService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.out.QRDynamic
import raiffeisen.sbp.sdk.model.out.QRId
import raiffeisen.sbp.sdk.model.out.QRStatic
import raiffeisen.sbp.sdk.model.out.QRVariable

@Service
class QrServiceImpl: QrService {

    override fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrDynamicDto.sbpMerchantId, qrDynamicDto.secretKey)
        val qrCode = QRDynamic(qrDynamicDto.order, qrDynamicDto.amount)
        qrCode.account = qrDynamicDto.account
        qrCode.additionalInfo = qrDynamicDto.additionalInfo
        qrCode.paymentDetails = qrDynamicDto.paymentDetails
        qrCode.qrExpirationDate = qrDynamicDto.qrExpirationDate
        return sbpClient.registerQR(qrCode)
    }

    override fun registerStaticQr(qrStaticDto: QrStaticDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrStaticDto.sbpMerchantId, qrStaticDto.secretKey)
        val qrCode = QRStatic(qrStaticDto.order)
        qrCode.account = qrStaticDto.account
        qrCode.additionalInfo = qrStaticDto.additionalInfo
        qrCode.paymentDetails = qrCode.paymentDetails
        qrCode.amount = qrStaticDto.amount
        qrCode.qrExpirationDate = qrCode.qrExpirationDate
        return sbpClient.registerQR(qrCode)
    }

    override fun registerVariableQr(qrVariableDto: QrVariableDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrVariableDto.sbpMerchantId, qrVariableDto.secretKey)
        val qrCode = QRVariable()
        qrCode.account = qrVariableDto.account
        return sbpClient.registerQR(qrCode)
    }

    override fun getQrInfo(qrId: String, sbpClientDto: SbpClientDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpClientDto.merchantId, sbpClientDto.secretKey)
        val id = QRId(qrId)
        val qrInfo = sbpClient.getQRInfo(id)
        return qrInfo
    }
}