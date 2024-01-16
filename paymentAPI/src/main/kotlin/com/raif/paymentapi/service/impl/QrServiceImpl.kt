package com.raif.paymentapi.service.impl

import com.raif.paymentapi.data.QrKeyRepository
import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.QrInformation
import com.raif.paymentapi.domain.model.QrKey
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.QrService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.out.QRDynamic
import raiffeisen.sbp.sdk.model.out.QRId
import raiffeisen.sbp.sdk.model.out.QRStatic
import raiffeisen.sbp.sdk.model.out.QRVariable
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Service
class QrServiceImpl(
    private val qrKeyRepository: QrKeyRepository
) : QrService {
    private val databaseApiClient: DatabaseApiClient = DatabaseApiClientImpl()

    override fun registerDynamicQr(qrDynamicDto: QrDynamicDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrDynamicDto.sbpMerchantId, qrDynamicDto.secretKey)
        val qrCode = QRDynamic(qrDynamicDto.order, qrDynamicDto.amount)
        qrCode.account = qrDynamicDto.account
        qrCode.additionalInfo = qrDynamicDto.additionalInfo
        qrCode.paymentDetails = qrDynamicDto.paymentDetails
        qrCode.qrExpirationDate = qrDynamicDto.qrExpirationDate
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        qrKeyRepository.save(QrKey(qrUrl.qrId, qrDynamicDto.secretKey, qrDynamicDto.sbpMerchantId))
        databaseApiClient.save(
            PaymentInformation(
                qrDynamicDto.additionalInfo?:"",
                qrDynamicDto.amount,
                ZonedDateTime.now(),
                qrDynamicDto.currency?:"",
                qrDynamicDto.order,
                qrDynamicDto.paymentDetails?:"",
                qrUrl.qrId,
                qrDynamicDto.sbpMerchantId,
                ZonedDateTime.now(),
                0
            )
        )
        return qrUrl
    }

    override fun registerStaticQr(qrStaticDto: QrStaticDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrStaticDto.sbpMerchantId, qrStaticDto.secretKey)
        val qrCode = QRStatic(qrStaticDto.order)
        qrCode.account = qrStaticDto.account
        qrCode.additionalInfo = qrStaticDto.additionalInfo
        qrCode.paymentDetails = qrCode.paymentDetails
        qrCode.amount = qrStaticDto.amount
        qrCode.qrExpirationDate = qrCode.qrExpirationDate
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        qrKeyRepository.save(QrKey(qrUrl.qrId, qrStaticDto.secretKey, qrStaticDto.sbpMerchantId))
        return qrUrl
    }

    override fun registerVariableQr(qrVariableDto: QrVariableDto): QRUrl {
        val sbpClient = SbpClient(SbpClient.TEST_URL, qrVariableDto.sbpMerchantId, qrVariableDto.secretKey)
        val qrCode = QRVariable()
        qrCode.account = qrVariableDto.account
        val qrUrl = sbpClient.registerQR(qrCode)
        databaseApiClient.save(QrInformation(qrUrl.qrId, qrUrl.qrStatus, qrUrl.payload, qrUrl.qrUrl))
        qrKeyRepository.save(QrKey(qrUrl.qrId, qrVariableDto.secretKey, qrVariableDto.sbpMerchantId))
        return qrUrl
    }

    override fun getQrInfo(qrId: String, sbpClientDto: SbpClientDto): QrInformation {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpClientDto.merchantId, sbpClientDto.secretKey)
        val id = QRId(qrId)
        val qrInfo = sbpClient.getQRInfo(id)
        return QrInformation(qrInfo.qrId, qrInfo.qrStatus, qrInfo.payload, qrInfo.qrUrl)
    }

    override fun getPaymentInfo(qrId: String, sbpClientDto: SbpClientDto): PaymentInformation {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpClientDto.merchantId, sbpClientDto.secretKey)
        val id = QRId(qrId)
        val paymentInfo = sbpClient.getPaymentInfo(id)
        return PaymentInformation(
            paymentInfo.additionalInfo?:"",
            paymentInfo.amount,
            paymentInfo.createDate,
            paymentInfo.currency,
            paymentInfo.order,
            paymentInfo.paymentStatus,
            paymentInfo.qrId,
            sbpClientDto.merchantId,
            paymentInfo.transactionDate?:paymentInfo.createDate,
            paymentInfo.transactionId
        )
    }
}