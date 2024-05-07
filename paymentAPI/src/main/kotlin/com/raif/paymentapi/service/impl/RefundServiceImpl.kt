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
import raiffeisen.sbp.sdk.model.out.OrderRefund
import raiffeisen.sbp.sdk.model.out.RefundId
import raiffeisen.sbp.sdk.model.out.RefundInfo
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs

@Service
class RefundServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
) : RefundService {
    private var refundIdList = CopyOnWriteArrayList<String>()
    private val orderRefundQueue = ArrayBlockingQueue<Pair<String, String>>(10)
    override fun makeRefund(refundDto: RefundDto): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val refundInfo = RefundInfo(refundDto.amount, refundDto.orderId, refundDto.refundId)

        refundInfo.paymentDetails = refundDto.paymentDetails
        val response = sbpClient.refundPayment(refundInfo)
        databaseApiClient.save(
            RefundInformation(
                refundDto.amount,
                refundDto.orderId,
                refundDto.refundId,
                response.refundStatus,
                refundDto.paymentDetails,
                abs(UUID.randomUUID().mostSignificantBits)
            )
        )
        refundIdList.add(refundDto.refundId)
        return response
    }

    override fun makeOrderRefund(refundDto: RefundDto): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val result = sbpClient.orderRefund(OrderRefund(refundDto.orderId, refundDto.refundId, refundDto.amount))
        databaseApiClient.save(
            RefundInformation(
                refundDto.amount,
                refundDto.orderId,
                refundDto.refundId,
                result.refundStatus,
                refundDto.paymentDetails,
                abs(UUID.randomUUID().mostSignificantBits)
            )
        )
        orderRefundQueue.add(Pair(refundDto.orderId, refundDto.refundId))
        return result
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
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val updatedList = CopyOnWriteArrayList<String>();
        for (refundId in refundIdList) {
            val refundStatus = sbpClient.getRefundInfo(RefundId(refundId))
            if (refundStatus.refundStatus != "IN_PROGRESS") {
                databaseApiClient.updateStatus(
                    "/database-api/v1/refund/",
                    refundId,
                    refundStatus.refundStatus
                )
            } else {
                updatedList.add(refundId)
            }
        }
        refundIdList = updatedList
    }

    @Scheduled(fixedDelay = 10000)
    private fun checkOrderRefundStatus() {
        var size = orderRefundQueue.size
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        while (size-- > 0) {
            val current = orderRefundQueue.poll()
            val status = sbpClient.orderRefundStatus(current.first, current.second).refundStatus
            if (status != "IN_PROGRESS") {
                databaseApiClient.updateStatus("/database-api/v1/refund/", current.second, status)
            } else {
                orderRefundQueue.add(current)
            }
        }
    }
}