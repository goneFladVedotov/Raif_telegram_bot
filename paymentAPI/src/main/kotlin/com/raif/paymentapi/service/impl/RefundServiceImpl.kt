package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.domain.model.RefundInformation
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.RefundService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.QRId
import raiffeisen.sbp.sdk.model.out.RefundId
import raiffeisen.sbp.sdk.model.out.RefundInfo
import java.util.concurrent.CopyOnWriteArrayList

@Service
class RefundServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
) : RefundService {
    private var refundIdList = CopyOnWriteArrayList<String>()
    override fun makeRefund(refundDto: RefundDto): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val refundInfo = RefundInfo(refundDto.amount, refundDto.orderId, refundDto.refundId)
        refundInfo.paymentDetails = refundDto.paymentDetails
        val paymentInfo = sbpClient.getPaymentInfo(QRId(refundDto.qrId))
        refundInfo.transactionId = paymentInfo.transactionId
        val response = sbpClient.refundPayment(refundInfo)
        databaseApiClient.save(
            RefundInformation(
                refundDto.amount,
                refundDto.orderId,
                refundDto.refundId,
                response.refundStatus,
                refundDto.paymentDetails,
                paymentInfo.transactionId
            )
        )
        refundIdList.add(refundDto.refundId)
        return response
    }

    override fun getRefundStatus(refundId: String): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getRefundInfo(RefundId(refundId))
    }

    @Scheduled(fixedDelay = 10000)
    override fun checkRefundStatus() {
        if (refundIdList.isEmpty()) {
            return
        }
        val updatedList = CopyOnWriteArrayList<String>();
        for (refundId in refundIdList) {
            val refundStatus = getRefundStatus(refundId)
            if (refundStatus.refundStatus != "IN_PROGRESS") {
                databaseApiClient.update("http://147.78.66.234:9091/database-api/v1/refund/", refundId, refundStatus.refundStatus)
            } else {
                updatedList.add(refundId)
            }
        }
        refundIdList = updatedList
    }
}