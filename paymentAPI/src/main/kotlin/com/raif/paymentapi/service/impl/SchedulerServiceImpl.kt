package com.raif.paymentapi.service.impl

import com.raif.paymentapi.data.QrKeyRepository
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.service.SchedulerService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerServiceImpl(
    private val qrKeyRepository: QrKeyRepository,
    private val qrService: QrService
) : SchedulerService {
    private val databaseApiClient: DatabaseApiClient = DatabaseApiClientImpl()

    @Scheduled(fixedDelay = 5000)
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
}