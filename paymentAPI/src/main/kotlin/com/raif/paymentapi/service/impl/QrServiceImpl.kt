package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.QrInformation
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.QrService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.PaymentInfo
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.out.QRDynamic
import raiffeisen.sbp.sdk.model.out.QRId
import raiffeisen.sbp.sdk.model.out.QRStatic
import raiffeisen.sbp.sdk.model.out.QRVariable
import java.time.ZonedDateTime

@Service
class QrServiceImpl(
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
) : QrService {
    private val databaseApiClient: DatabaseApiClient = DatabaseApiClientImpl()

    override fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val qrCode = QRDynamic(qrDynamicDto.order, qrDynamicDto.amount)
        qrCode.account = qrDynamicDto.account
        qrCode.additionalInfo = qrDynamicDto.additionalInfo
        qrCode.paymentDetails = qrDynamicDto.paymentDetails
        qrCode.qrExpirationDate = qrDynamicDto.qrExpirationDate
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        databaseApiClient.save(
            PaymentInformation(
                qrDynamicDto.additionalInfo ?: "",
                qrDynamicDto.amount,
                ZonedDateTime.now(),
                qrDynamicDto.currency ?: "",
                qrDynamicDto.order,
                qrDynamicDto.paymentDetails ?: "",
                qrUrl.qrId,
                sbpMerchantId,
                ZonedDateTime.now(),
                0
            )
        )
        return qrUrl
    }

    override fun registerStaticQr(qrStaticDto: QrStaticDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val qrCode = QRStatic(qrStaticDto.order)
        qrCode.account = qrStaticDto.account
        qrCode.additionalInfo = qrStaticDto.additionalInfo
        qrCode.paymentDetails = qrCode.paymentDetails
        qrCode.amount = qrStaticDto.amount
        qrCode.qrExpirationDate = qrCode.qrExpirationDate
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        return qrUrl
    }

    override fun registerVariableQr(qrVariableDto: QrVariableDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val qrCode = QRVariable()
        qrCode.account = qrVariableDto.account
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        return qrUrl
    }

    override fun getQrInfo(qrId: String): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val id = QRId(qrId)
        return sbpClient.getQRInfo(id)
    }

    override fun getPaymentInfo(qrId: String): PaymentInfo {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val id = QRId(qrId)
        return sbpClient.getPaymentInfo(id)
    }
}