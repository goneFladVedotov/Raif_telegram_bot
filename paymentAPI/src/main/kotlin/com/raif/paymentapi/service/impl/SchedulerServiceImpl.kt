package com.raif.paymentapi.service.impl

import com.raif.paymentapi.data.QrKeyRepository
import com.raif.paymentapi.data.RefundKeyRepository
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.service.RefundService
import com.raif.paymentapi.service.SchedulerService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchedulerServiceImpl(
    private val qrKeyRepository: QrKeyRepository,
    private val qrService: QrService,
    private val refundService: RefundService,
    val refundKeyRepository: RefundKeyRepository
) : SchedulerService {
    private val databaseApiClient: DatabaseApiClient = DatabaseApiClientImpl()

    @Scheduled(fixedDelay = 5000)
    @Transactional
    override fun updateQrInfo() {
        val qrKeys = qrKeyRepository.findAll()
        if (qrKeys.isEmpty())
            return
        for (key in qrKeys) {
            val sbpClientDto = SbpClientDto(key.merchantId, key.secretKey)
            val qrInfo = qrService.getQrInfo(key.qrId, sbpClientDto)
            if (qrInfo.qrStatus != "NEW" && qrInfo.qrStatus != "IN_PROGRESS") {
                qrKeyRepository.deleteByQrId(qrInfo.qrId)
            }
            databaseApiClient.update(qrInfo)
            val paymentInfo = qrService.getPaymentInfo(key.qrId, sbpClientDto)
            databaseApiClient.update(paymentInfo)
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    override fun updateRefundStatus() {
        val refundKeys = refundKeyRepository.findAll()
        if (refundKeys.isEmpty()) {
            return
        }
        for (key in refundKeys) {
            val sbpClientDto = SbpClientDto(key.merchantId, key.secretKey)
            val refundStatus = refundService.getRefundStatus(key.refundId, sbpClientDto)
            if (refundStatus.refundStatus != "IN_PROGRESS") {
                refundKeyRepository.deleteByRefundId(key.refundId)
            }
            databaseApiClient.update(key.refundId, refundStatus.refundStatus)
        }
    }
}