package com.raif.paymentapi.service.impl

import com.raif.paymentapi.data.RefundKeyRepository
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.domain.model.RefundInformation
import com.raif.paymentapi.domain.model.RefundKey
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.RefundService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.RefundId
import raiffeisen.sbp.sdk.model.out.RefundInfo
import java.util.UUID

@Service
class RefundServiceImpl(
    private val refundKeyRepository: RefundKeyRepository,
    private val databaseApiClient: DatabaseApiClient
) : RefundService {
    override fun makeRefund(refundDto: RefundDto): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, refundDto.merchantId, refundDto.secretKey)
        val refundInfo = RefundInfo(
            refundDto.amount,
            refundDto.orderId,
            refundDto.refundId
        )
        refundInfo.paymentDetails = refundDto.paymentDetails
        val response = sbpClient.refundPayment(refundInfo)
        databaseApiClient.save(RefundInformation(refundDto.amount, refundDto.orderId, refundDto.refundId, refundDto.paymentDetails, UUID.randomUUID().timestamp()))
        refundKeyRepository.save(RefundKey(refundDto.refundId, refundDto.secretKey, refundDto.merchantId))
        return response
    }

    override fun getRefundStatus(refundId: String, sbpClientDto: SbpClientDto): RefundStatus {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpClientDto.merchantId, sbpClientDto.secretKey)
        return sbpClient.getRefundInfo(RefundId(refundId))
    }
}